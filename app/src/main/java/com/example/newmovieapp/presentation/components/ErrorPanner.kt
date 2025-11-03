package com.example.newmovieapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.moviesap.R

@Composable
fun ErrorBanner(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.medium_spacer)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier= Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = message,
                color = colorResource(R.color.gry),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding))
            )
            Button(onClick = onRetry) { Text(stringResource(R.string.try_again)) }
        }
    }
}