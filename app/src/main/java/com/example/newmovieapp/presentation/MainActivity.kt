package com.example.newmovieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.newmovieapp.presentation.components.PostsApp
import com.example.newmovieapp.presentation.theme.NewMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewMovieAppTheme {
                Scaffold { innerPadding ->
                    PostsApp(
                        modifier = Modifier.Companion
                            .padding(innerPadding)
                            .windowInsetsPadding(WindowInsets.Companion.statusBars)
                    )
                }
            }
        }
    }
}