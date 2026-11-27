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

    private var submitJob: Job? = null

    init {
        loadPosts()
        observeKeyword()
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.KeywordChanged -> changeKeyword(intent.keyword)
            is PostIntent.TitleChanged -> changeTitle(intent.title)
            is PostIntent.ContentChanged -> changeContent(intent.content)
            is PostIntent.CreateClicked -> create()
            is PostIntent.EditStarted -> startEdit(intent.id)
            is PostIntent.EditDismissed -> endEdit()
            is PostIntent.EditClicked -> edit()
            is PostIntent.DeleteClicked -> delete(intent.id)
            is PostIntent.SearchClicked -> onSearchClick()
            is PostIntent.LoadMore -> loadMorePosts()
        }
    }

    private fun loadPosts(keyword: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            postRepository.getPosts(
                keyword = keyword?.trim()?.ifBlank { null },
                cursor = null
            )
                .onSuccess { result ->
                    _state.update { it.copy(
                        posts = result.posts.map { post ->
                            post.toUiModel()
                        },
                        nextCursor = result.nextCursor,
                        hasNext = result.hasNext,
                        isLoading = false,
                    )
                    }
                }
                .onFailure {
            _state.update { it.copy(isLoading = false) }
            _sideEffect.send(PostSideEffect.ShowToast(R.string.post_load_failure))
        }
        }
    }

    private fun loadMorePosts() {
        val state = _state.value

        if (!state.hasNext || state.isLoadingMore || state.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }

            postRepository.getPosts(
                keyword = state.keyword.trim().ifBlank { null },
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
        _state.update { state ->
            state.copy(keyword = keyword)
        }
    }

    private fun changeTitle(title: String) {
        _state.update { state ->
            state.copy(title = title)
        }
    }

    private fun changeContent(content: String) {
        _state.update { state ->
            state.copy(content = content)
        }
    }

    private fun create() {
        if (submitJob?.isActive == true) return

        val state = _state.value
        submitJob = viewModelScope.launch {
            postRepository.createPost(
                title = state.title,
                content = state.content,
                author = authRepository.getUserName(),
            )
                .onSuccess { loadPosts(keyword = _state.value.keyword) }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_create_failure))
                }
        }
    }

    private fun startEdit(id: Long) {
        _state.update { state ->
            state.copy(editingPost = state.posts.find { it.id == id })
        }
    }

    private fun endEdit() {
        _state.update { it.copy(editingPost = null) }
    }

    private fun edit() {
        if (submitJob?.isActive == true) return

        val state = _state.value
        val post = state.editingPost ?: return
        endEdit()

        submitJob = viewModelScope.launch {
            postRepository.editPost(
                postId = post.id,
                title = state.title,
                content = state.content,
            )
                .onSuccess { loadPosts(keyword = state.keyword) }
                .onFailure { _sideEffect.send(PostSideEffect.ShowToast(R.string.post_edit_failure)) }
        }
    }

    private fun delete(id: Long) {
        if (submitJob?.isActive == true) return

        submitJob = viewModelScope.launch {
            postRepository.deletePost(
                postId = id
            )
                .onSuccess { loadPosts(keyword = _state.value.keyword) }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_delete_failure))
                }
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
    private fun observeKeyword() {
        val typed = _state
            .map { it.keyword }
            .distinctUntilChanged()
            .drop(1)
            .debounce(300)

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
    }
}
