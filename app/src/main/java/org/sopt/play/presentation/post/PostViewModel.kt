package org.sopt.play.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.R
import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.PostRepository
import org.sopt.play.presentation.post.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state: StateFlow<PostState> = _state.asStateFlow()

    private val _sideEffect = Channel<PostSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val searchClicks = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private var loadJob: Job? = null

    init {
        loadPosts(keyword = null)
        observeSearch()
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.KeywordChanged -> changeKeyword(intent.keyword)
            is PostIntent.TitleChanged -> changeTitle(intent.title)
            is PostIntent.ContentChanged -> changeContent(intent.content)

            PostIntent.SearchClicked -> onSearchClick()
            PostIntent.LoadMore -> loadMorePosts()

            PostIntent.CreateSheetOpened -> openCreateSheet()
            PostIntent.CreateSheetDismissed -> closeCreateSheet()
            PostIntent.CreateClicked -> create()

            is PostIntent.EditStarted -> startEdit(intent.id)
            PostIntent.EditDismissed -> dismissEdit()
            PostIntent.EditClicked -> edit()

            is PostIntent.DeleteRequested -> requestDelete(intent.id)
            PostIntent.DeleteDismissed -> dismissDelete()
            PostIntent.DeleteConfirmed -> delete()
        }
    }

    private fun loadPosts(keyword: String?) {
        val searchKeyword = keyword?.trim()?.ifBlank { null }

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isLoadingMore = false) }

            postRepository.getPosts(
                keyword = searchKeyword,
                cursor = null,
            )
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            posts = result.posts.map { post -> post.toUiModel() },
                            searchedKeyword = searchKeyword,
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext,
                            isLoading = false,
                            isLoadFailed = false,
                        )
                    }
                }
                .onFailure {
                    _state.update { it.copy(isLoading = false, isLoadFailed = true) }
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_load_failure))
                }
        }
    }

    private fun refreshPosts() {
        loadPosts(keyword = _state.value.searchedKeyword)
    }

    private fun loadMorePosts() {
        val state = _state.value
        if (!state.hasNext || state.isLoadingMore || state.isLoading) return

        loadJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }

            postRepository.getPosts(
                keyword = state.searchedKeyword,
                cursor = state.nextCursor,
            )
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            posts = it.posts + result.posts.map { post -> post.toUiModel() },
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext,
                            isLoadingMore = false,
                        )
                    }
                }
                .onFailure {
                    _state.update { it.copy(isLoadingMore = false) }
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_load_failure))
                }
        }
    }

    private fun changeKeyword(keyword: String) {
        _state.update { it.copy(keyword = keyword) }
    }

    private fun changeTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    private fun changeContent(content: String) {
        _state.update { it.copy(content = content) }
    }

    private fun openCreateSheet() {
        _state.update { it.copy(isCreateSheetVisible = true) }
    }

    private fun closeCreateSheet() {
        _state.update { it.copy(isCreateSheetVisible = false) }
    }

    private fun create() {
        val state = _state.value
        if (state.isSubmitting) return
        _state.update { it.copy(isSubmitting = true) }

        viewModelScope.launch {
            postRepository.createPost(
                title = state.title,
                content = state.content,
                author = authRepository.getUserName(),
            )
                .onSuccess {
                    _state.update { it.copy(isCreateSheetVisible = false) }
                    _sideEffect.send(PostSideEffect.ClearInputs)
                    refreshPosts()
                }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_create_failure))
                }

            _state.update { it.copy(isSubmitting = false) }
        }
    }

    private fun startEdit(id: Long) {
        _state.update { state ->
            state.copy(editingPost = state.posts.find { it.id == id })
        }
    }

    private fun dismissEdit() {
        _state.update { it.copy(editingPost = null) }
        viewModelScope.launch { _sideEffect.send(PostSideEffect.ClearInputs) }
    }

    private fun edit() {
        val state = _state.value
        if (state.isSubmitting) return
        val post = state.editingPost ?: return
        _state.update { it.copy(isSubmitting = true) }

        viewModelScope.launch {
            postRepository.editPost(
                postId = post.id,
                title = state.title,
                content = state.content,
            )
                .onSuccess {
                    _state.update { it.copy(editingPost = null) }
                    _sideEffect.send(PostSideEffect.ClearInputs)
                    refreshPosts()
                }
                .onFailure { _sideEffect.send(PostSideEffect.ShowToast(R.string.post_edit_failure)) }

            _state.update { it.copy(isSubmitting = false) }
        }
    }

    private fun requestDelete(id: Long) {
        _state.update { it.copy(deletingPostId = id) }
    }

    private fun dismissDelete() {
        _state.update { it.copy(deletingPostId = null) }
    }

    private fun delete() {
        val state = _state.value
        if (state.isSubmitting) return
        val postId = state.deletingPostId ?: return
        _state.update { it.copy(isSubmitting = true) }

        viewModelScope.launch {
            postRepository.deletePost(postId = postId)
                .onSuccess { refreshPosts() }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_delete_failure))
                }

            _state.update { it.copy(deletingPostId = null, isSubmitting = false) }
        }
    }

    private fun onSearchClick() {
        if (_state.value.keyword.trim().length in 1 until MIN_KEYWORD_LENGTH) {
            viewModelScope.launch {
                _sideEffect.send(PostSideEffect.ShowToast(R.string.post_search_keyword_too_short))
            }
            return
        }
        searchClicks.tryEmit(Unit)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        val typed = _state
            .map { it.keyword }
            .distinctUntilChanged()
            .drop(1)
            .debounce(SEARCH_DEBOUNCE_MILLIS)

        val clicked = searchClicks.map { _state.value.keyword }

        viewModelScope.launch {
            merge(typed, clicked)
                .map { it.trim() }
                .filter { it.isEmpty() || it.length >= MIN_KEYWORD_LENGTH }
                .distinctUntilChanged()
                .collectLatest { keyword -> loadPosts(keyword = keyword) }
        }
    }

    companion object {
        private const val MIN_KEYWORD_LENGTH = 2
        private const val SEARCH_DEBOUNCE_MILLIS = 300L
    }
}
