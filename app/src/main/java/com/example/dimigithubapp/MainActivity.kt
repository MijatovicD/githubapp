package com.example.dimigithubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dimigithubapp.reporistorydetail.RepositoryDetailScreen
import com.example.dimigithubapp.userrepository.UserRepositoriesScreen
import com.example.dimigithubapp.userrepository.UserRepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: UserRepositoryViewModel by viewModel()

        setContent {
            MaterialTheme {
                val uiState by viewModel.userRepositoryListUiStateFlow.collectAsState()

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "UserRepositories"
                ) {
                    composable("UserRepositories") {
                        UserRepositoriesScreen(
                            navController = navController,
                            onRetryClick = { viewModel.getUserRepositories() },
                            uiState = uiState,
                        )
                    }
                    composable(
                        route = "RepositoryDetailScreen/{repositoryName}",
                        arguments = listOf(navArgument("repositoryName") {
                            type = NavType.StringType
                        })
                    ) {
                        RepositoryDetailScreen(
                            entry = it,
                            onBackClick = { navController.navigateUp() },
                        )
                    }
                }
            }
        }
    }
}
