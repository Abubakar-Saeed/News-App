package com.practice.newsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practice.newsapp.ui.theme.NewsAppTheme
import com.practice.view.MainScreen
import com.practice.view.NewsScreen
import com.practice.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel = viewModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(viewModel: NewsViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainScreen") {

        composable("MainScreen") {
            MainScreen(viewModel,navController)
        }

        composable("NewsScreen") {
            NewsScreen(navController,viewModel)
        }

    }


}
