package com.example.dimigithubapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dimigithubapp.compose.general.ErrorScreen
import com.example.dimigithubapp.compose.general.LoadingScreen
import com.example.dimigithubapp.compose.general.UserRepositoryPreviewParameter
import com.example.dimigithubapp.model.UserRepositoryUiModel
import com.example.dimigithubapp.model.UserRepositoryUiState

@Composable
fun UserRepositoriesScreen(navController: NavController, uiState: UserRepositoryUiState) {
    when (uiState) {
        UserRepositoryUiState.Loading -> LoadingScreen()
        UserRepositoryUiState.EmptyList -> EmptyListContent()
        is UserRepositoryUiState.Complete -> ListContent(
            repositories = uiState.uiModel,
            onRepositoryClicked = { navController.navigate("RepositoryDetailScreen/$it") },
        )
        is UserRepositoryUiState.Error -> ErrorScreen(uiState = uiState)
    }
}

@Composable
private fun EmptyListContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary),
    ) {
        Text(
            text = stringResource(id = R.string.repository_blank_screen),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ListContent(
    repositories: List<UserRepositoryUiModel>,
    onRepositoryClicked: (repositoryName: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary),
    ) {
        items(repositories) { uiModel ->
            UserRepositoryContent(uiModel = uiModel, onRepositoryClicked = onRepositoryClicked)
        }
    }
}

@Composable
private fun UserRepositoryContent(
    uiModel: UserRepositoryUiModel,
    onRepositoryClicked: (repositoryName: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onRepositoryClicked(uiModel.repositoryName) },
    ) {
        Text(
            text = stringResource(id = R.string.repository_id, uiModel.id),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(id = R.string.repository_name, uiModel.repositoryName),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = stringResource(id = R.string.repository_open_issues, uiModel.openIssueCount),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun UserRepositoryScreenPreview(
    @PreviewParameter(UserRepositoryPreviewParameter::class) userRepositoryUiModel: List<UserRepositoryUiModel>,
) {
    MaterialTheme {
        ListContent(
            repositories = userRepositoryUiModel,
            onRepositoryClicked = {}
        )
    }
}

@Preview
@Composable
private fun EmptyListContentPreview() {
    MaterialTheme {
        EmptyListContent()
    }
}