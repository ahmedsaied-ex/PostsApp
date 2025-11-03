package com.example.newmovieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newmovieapp.presentation.navigation.BottomNavigationBar
import com.example.newmovieapp.presentation.navigation.HomeRoute
import com.example.newmovieapp.presentation.navigation.HomeScreen
import com.example.newmovieapp.presentation.navigation.ProfileRoute
import com.example.newmovieapp.presentation.navigation.ProfileScreen
import com.example.newmovieapp.presentation.navigation.SearchRoute
import com.example.newmovieapp.presentation.navigation.SearchScreen
import com.example.newmovieapp.presentation.theme.NewMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewMovieAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> { HomeScreen() }
            composable<ProfileRoute> { ProfileScreen() }
            composable<SearchRoute> { SearchScreen() }
        }
    }
}