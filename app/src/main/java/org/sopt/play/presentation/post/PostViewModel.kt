package org.sopt.play.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.domain.repository.PostRepository
import org.sopt.play.presentation.post.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state: StateFlow<PostState> = _state.asStateFlow()

    init {
        loadPosts(cursor = null)
//        _state.update { it.copy(posts = dummyPosts) }

    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.SearchChanged -> changeSearch(intent.search)
            is PostIntent.TitleChanged -> changeTitle(intent.title)
            is PostIntent.ContentChanged -> changeContent(intent.content)
            is PostIntent.CompleteClicked -> complete()
            PostIntent.DeleteClicked -> delete()
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

    }

    private fun delete() {

    }
}
