package com.example.newmovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesap.R
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.common.ResourceProvider
import com.example.newmovieapp.data.domain.use_case.CreatePostUseCase
import com.example.newmovieapp.data.domain.use_case.GetPostsUseCase
import com.example.newmovieapp.data.local.model.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val posts = getPostsUseCase().cachedIn(viewModelScope)

    private val _createPostState = MutableStateFlow<Resource<PostEntity>>(Resource.Idle())
    val createPostState: StateFlow<Resource<PostEntity>> = _createPostState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    fun addPost(title: String, body: String) {
        viewModelScope.launch {
            val post = PostEntity(userId = 11, title = title, body = body)

            createPostUseCase(post).collect { result ->
                _createPostState.value = result

                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            val msg = resourceProvider.getString(R.string.post_added_successfully)
                            _eventFlow.emit(UiEvent.ShowToast("${it.title} $msg"))
                        }
                    }
                    is Resource.Error -> {
                        val msg = result.message
                            ?: resourceProvider.getString(R.string.error_creating_post)
                        _eventFlow.emit(UiEvent.ShowToast(msg))
                    }
                    else -> Unit
                }
            }
        }
    }
}
