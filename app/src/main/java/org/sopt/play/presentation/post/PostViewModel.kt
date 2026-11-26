package org.sopt.play.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
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

    init {
        loadPosts()
        observeKeyword()
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.KeywordChanged -> changeKeyword(intent.keyword)
            is PostIntent.TitleChanged -> changeTitle(intent.title)
            is PostIntent.ContentChanged -> changeContent(intent.content)
            is PostIntent.CompleteClicked -> complete()
            is PostIntent.DeleteClicked -> delete(intent.id)
            is PostIntent.SearchClicked -> onSearchClick()
        }
    }

    private fun loadPosts(keyword: String? = null, cursor: Int? = null){
        viewModelScope.launch {
            postRepository.getPosts(
                keyword = keyword?.trim()?.ifBlank { null },
                cursor = cursor
            )
                .onSuccess { result ->
                    _state.update { it.copy(
                        posts = result.posts.map { post ->
                            post.toUiModel()
                        },
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

    private fun complete() {
        val state = _state.value
        viewModelScope.launch {
            postRepository.createPost(
                title = state.title,
                content = state.content,
                author = authRepository.getUserName(),
            )
                .onSuccess { loadPosts() }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_create_failure))
                }
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            postRepository.deletePost(
                postId = id
            )
                .onSuccess { loadPosts() }
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
