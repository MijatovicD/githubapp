package com.example.dimigithubapp.userrepository

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dimigithubapp.R
import com.example.dimigithubapp.compose.general.EmptyListContent
import com.example.dimigithubapp.compose.general.ErrorScreen
import com.example.dimigithubapp.compose.general.LoadingScreen
import com.example.dimigithubapp.compose.general.TopBar
import com.example.dimigithubapp.compose.general.UserRepositoryPreviewParameter
import com.example.dimigithubapp.model.UserRepositoryUiModel
import com.example.dimigithubapp.model.UserRepositoryUiState

@Composable
fun UserRepositoriesScreen(
    navController: NavController,
    onRetryClick: () -> Unit,
    uiState: UserRepositoryUiState,
) {
    when (uiState) {
        UserRepositoryUiState.Loading -> LoadingScreen()
        UserRepositoryUiState.EmptyList -> EmptyListContent(textResourceId = R.string.repository_blank_screen)
        is UserRepositoryUiState.Complete -> ListContent(
            repositories = uiState.uiModel,
            onRepositoryClicked = { repositoryName ->
                navController.navigate("RepositoryDetailScreen/$repositoryName")
            },
        )

        is UserRepositoryUiState.Error -> ErrorScreen(
            uiState = uiState,
            onRetryClick = onRetryClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    repositories: List<UserRepositoryUiModel>,
    onRepositoryClicked: (repositoryName: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.topbar_main),
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.onPrimary),
        ) {
            items(repositories) { uiModel ->
                UserRepositoryContent(uiModel = uiModel, onRepositoryClicked = onRepositoryClicked)
            }
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
            onRepositoryClicked = {},
        )
    }
}