package com.example.newmovieapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity


@Composable
fun AddPost(
    onAddPost: (String, String) -> Unit,
    createPostState: Resource<PostEntity>
) {
    var title by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onAddPost(title.trim(), body.trim())
                title = ""
                body = ""
            },
            enabled = title.isNotBlank() && body.isNotBlank() && createPostState !is Resource.Loading,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            if (createPostState is Resource.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 8.dp),
                    strokeWidth = 2.dp
                )
            }
            Text("Add Post")
        }
    }
}