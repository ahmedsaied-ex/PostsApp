package com.example.newmovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.domain.use_case.CreatePostUseCase
import com.example.newmovieapp.data.domain.use_case.GetPostsUseCase
import com.example.newmovieapp.data.local.model.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    val posts = getPostsUseCase().cachedIn(viewModelScope) // كده الداتا هاتفضل موجوده طزل ما ال ViewModel عايش

    private val _createPostState = MutableStateFlow<Resource<PostEntity>>(Resource.Idle())
    val createPostState: StateFlow<Resource<PostEntity>> = _createPostState

    fun addPost(title: String, body: String) {
        viewModelScope.launch {
            val post = PostEntity(userId = 11, title = title, body = body)
            createPostUseCase(post).collect { result ->
                _createPostState.value = result
            }
        }
    }
}
