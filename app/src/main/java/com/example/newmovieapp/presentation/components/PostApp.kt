package com.example.newmovieapp.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.presentation.PostViewModel

@Composable
fun PostsApp(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val createPostState by viewModel.createPostState.collectAsState()
    val context = LocalContext.current

    // ✅ Move Toast side effects here (SRP improvement)
    LaunchedEffect(createPostState) {
        when (createPostState) {
            is Resource.Error -> {
                Toast.makeText(
                    context,
                    createPostState.message ?: "Error creating post",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is Resource.Success -> {
                if (createPostState.data?.id != -1) {
                    Toast.makeText(
                        context,
                        "${createPostState.data?.title} added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> Unit
        }
    }

    PostScreenContent(
        posts = posts,
        createPostState = createPostState,
        onAddPost = { title, body -> viewModel.addPost(title, body) },
        modifier = modifier
    )
}
