package com.example.newmovieapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.newmovieapp.presentation.componentsprivate.ErrorBanner

@Composable
fun PostsContent(
    modifier: Modifier,
    posts: LazyPagingItems<PostEntity>,
    createPostState: Resource<PostEntity>,
    errorMessage: String?,
    onRefresh: () -> Unit,
    onAddPost: (String, String) -> Unit
) {
    val isLoading = posts.loadState.refresh is LoadState.Loading
    val hasData = posts.itemCount > 0
    val isError = errorMessage != null

    Column(modifier = modifier.fillMaxSize()) {

        AnimatedVisibility(visible = isError, enter = fadeIn(), exit = fadeOut()) {
            ErrorBanner(message = errorMessage ?: "", onRetry = onRefresh)
        }

        AnimatedVisibility(visible = isLoading && !hasData && !isError, enter = fadeIn(), exit = fadeOut()) {
            LoadingView("Loading posts...")
        }

        AnimatedVisibility(visible = !isLoading && !hasData && !isError, enter = fadeIn(), exit = fadeOut()) {
            EmptyStateView("No posts found")
        }

        AnimatedVisibility(visible = !isError && hasData, enter = fadeIn(), exit = fadeOut()) {
            PostScreenContent(
                posts = posts,
                createPostState = createPostState,
                onAddPost = onAddPost,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


