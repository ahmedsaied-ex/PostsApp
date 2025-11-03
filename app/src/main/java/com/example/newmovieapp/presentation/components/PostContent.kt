package com.example.newmovieapp.presentation.components


import androidx.compose.animation.core.tween
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.moviesap.R
import com.example.newmovieapp.common.PostsUiState
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity


@Composable
fun PostsContent(
    posts: LazyPagingItems<PostEntity>,
    createPostState: Resource<PostEntity>,
    errorMessage: String?,
    onRefresh: () -> Unit,
    onAddPost: (String, String) -> Unit
) {
    // derive UI state
    val isLoading = posts.loadState.refresh is LoadState.Loading
    val hasData = posts.itemCount > 0
    val isError = errorMessage != null

    val uiState: PostsUiState = when {
        isError -> PostsUiState.Error(errorMessage ?: "")
        isLoading && !hasData -> PostsUiState.Loading
        !hasData -> PostsUiState.Empty
        else -> PostsUiState.Success
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.medium_padding))
    ) {
        Crossfade(
            targetState = uiState,
            animationSpec = tween(durationMillis = 1000),
            label = "Posts Crossfade"
        ) { state ->
            when (state) {
                is PostsUiState.Loading -> {
                    LoadingView(stringResource(R.string.loading_posts   ))
                }

                is PostsUiState.Error -> {
                    ErrorBanner(message = "there was an error \n try again later", onRetry = onRefresh)
                }

                is PostsUiState.Empty -> {
                    EmptyStateView(stringResource(R.string.no_posts_found))
                }

                is PostsUiState.Success -> {
                    PostScreenContent(
                        posts = posts,
                        createPostState = createPostState,
                        onAddPost = onAddPost
                    )
                }
            }
        }
    }
}