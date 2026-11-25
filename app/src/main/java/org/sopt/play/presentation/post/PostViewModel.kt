package org.sopt.play.presentation.post

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.play.presentation.post.model.dummyPosts

class PostViewModel() : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state: StateFlow<PostState> = _state.asStateFlow()

    init {
        _state.update { it.copy(posts = dummyPosts) }
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
