package com.example.dimigithubapp.compose.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dimigithubapp.R
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
fun EmptyListContent(textResourceId: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = textResourceId),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ErrorScreen(uiState: UserRepositoryUiState.Error, onRetryClick: () -> Unit) {
    when (uiState) {
        UserRepositoryUiState.Error.Connection -> ConnectionErrorScreen(onRetryClick)
        UserRepositoryUiState.Error.Unknown -> UnknownErrorScreen()
    }
}

@Composable
fun ConnectionErrorScreen(onRetryClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = stringResource(id = R.string.connection_error))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetryClick) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    Text(text = stringResource(id = R.string.retry_button))
                }
            }
        }
    }
}

@Composable
fun UnknownErrorScreen() {
    Box {
        Text(text = stringResource(id = R.string.unknown_error))
    }
}

@Preview
@Composable
private fun EmptyListContentPreview() {
    MaterialTheme {
        EmptyListContent(textResourceId = R.string.repository_blank_screen)
    }
}

@Preview
@Composable
fun ConnectionErrorScreenPreview() {
    MaterialTheme {
        ConnectionErrorScreen(
            onRetryClick = {},
        )
    }
}