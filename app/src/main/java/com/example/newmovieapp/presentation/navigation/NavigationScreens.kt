package com.example.newmovieapp.presentation.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.presentation.PostViewModel
import com.example.newmovieapp.presentation.components.PostsContent

@Composable
fun SearchScreen() {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Search Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ProfileScreen() {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile Screen", style = MaterialTheme.typography.headlineMedium)
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val createPostState by viewModel.createPostState.collectAsState()
    val context = LocalContext.current

    var errorMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(createPostState) {
        when (createPostState) {
            is Resource.Error -> Toast.makeText(
                context,
                createPostState.message ?: "Error creating post",
                Toast.LENGTH_SHORT
            ).show()

            is Resource.Success -> createPostState.data?.let {
                if (it.id != -1)
                    Toast.makeText(context, "${it.title} added successfully!", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }


    LaunchedEffect(posts.loadState) {
        val errorState = when {
            posts.loadState.refresh is LoadState.Error -> posts.loadState.refresh as LoadState.Error
            posts.loadState.append is LoadState.Error -> posts.loadState.append as LoadState.Error
            posts.loadState.prepend is LoadState.Error -> posts.loadState.prepend as LoadState.Error
            else -> null
        }

        if (errorState != null && posts.itemCount == 0) {
            errorMessage = "Error loading posts: ${errorState.error.message}"
        } else if (posts.itemCount > 0) {
            errorMessage = null
        }
    }

    PostsContent(
        modifier = modifier,
        posts = posts,
        createPostState = createPostState,
        errorMessage = errorMessage,
        onRefresh = {
            errorMessage = null
            posts.refresh()
        },
        onAddPost = { title, body -> viewModel.addPost(title, body) }
    )
}



