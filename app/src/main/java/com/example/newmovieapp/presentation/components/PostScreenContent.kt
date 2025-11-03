package com.example.newmovieapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.moviesap.R
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenContent(
    posts: LazyPagingItems<PostEntity>,
    createPostState: Resource<PostEntity>,
    onAddPost: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // 👇 When a post is successfully created, scroll to the top
    LaunchedEffect(createPostState) {
        if (createPostState is Resource.Success && createPostState.data != null) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = stringResource(R.string.add_new_post),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        AddPost(
            onAddPost = onAddPost,
            createPostState = createPostState
        )

        Spacer(modifier = Modifier.height(16.dp))
        PostList(posts = posts , listState= listState)
    }
}
