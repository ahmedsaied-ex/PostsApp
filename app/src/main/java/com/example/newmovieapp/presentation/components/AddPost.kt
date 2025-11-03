package com.example.newmovieapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviesap.R
import com.example.newmovieapp.common.Resource
import com.example.newmovieapp.data.local.model.PostEntity


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddPost(
    onAddPost: (String, String) -> Unit,
    createPostState: Resource<PostEntity>
) {
    var title by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.post_title)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = body,
            onValueChange = { body = it },
            label = { Text(stringResource(R.string.post_body)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                focusManager.clearFocus()

                onAddPost(title.trim(), body.trim())
                title = ""
                body = ""
            },
            enabled = title.isNotBlank() && body.isNotBlank() && createPostState !is Resource.Loading,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium_padding))
        ) {
            if (createPostState is Resource.Loading) {
                CircularWavyProgressIndicator(
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.medium_padding))
                        .size(dimensionResource(id = R.dimen.small_Indicator))
                )
            }
            Text(stringResource(R.string.add_post))
        }
    }
}