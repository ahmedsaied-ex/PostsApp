package com.example.newmovieapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newmovieapp.data.local.model.PostEntity
import com.example.moviesap.R


@Composable
fun PostItem(post: PostEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id=R.dimen.small_height)))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
        }
    }
}
