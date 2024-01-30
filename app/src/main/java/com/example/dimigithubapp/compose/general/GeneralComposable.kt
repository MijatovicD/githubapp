package com.example.dimigithubapp.compose.general

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dimigithubapp.model.UserRepositoryUiState

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
    }
}

@Composable
fun ErrorScreen(uiState: UserRepositoryUiState.Error) {
    when (uiState) {
        UserRepositoryUiState.Error.Connection -> ConnectionErrorScreen()
        UserRepositoryUiState.Error.Unknown -> UnknownErrorScreen()
    }
}

@Composable
fun ConnectionErrorScreen() {
    Box {
        Text(text = "Sorry please turn your internet.")
    }
}

@Composable
fun UnknownErrorScreen() {
    Box {
        Text(text = "There is an error, please try later..")
    }
}