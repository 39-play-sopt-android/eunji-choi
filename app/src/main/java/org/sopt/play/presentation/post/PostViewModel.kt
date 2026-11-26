package org.sopt.play.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        loadPosts(cursor = null)
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.SearchChanged -> changeSearch(intent.search)
            is PostIntent.TitleChanged -> changeTitle(intent.title)
            is PostIntent.ContentChanged -> changeContent(intent.content)
            is PostIntent.CompleteClicked -> complete()
            is PostIntent.DeleteClicked -> delete(intent.id)
        }
    }

    fun loadPosts(cursor: Int?){
        viewModelScope.launch {
            postRepository.getPosts(cursor = cursor)
                .onSuccess { result ->
                    _state.update { it.copy(posts = result.posts.map { post -> post.toUiModel() }) }
                }
        }
    }

    private fun changeSearch(search: String) {
        _state.update { state ->
            state.copy(search = search)
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
                .onSuccess { loadPosts(cursor = null) }
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
                .onSuccess { loadPosts(cursor = null) }
                .onFailure {
                    _sideEffect.send(PostSideEffect.ShowToast(R.string.post_delete_failure))
                }
        }
    }
}
