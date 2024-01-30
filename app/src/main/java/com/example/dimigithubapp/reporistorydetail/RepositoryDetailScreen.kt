package com.example.dimigithubapp.reporistorydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.example.dimigithubapp.compose.general.LoadingScreen
import com.example.dimigithubapp.model.RepositoryDetailUiState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RepositoryDetailScreen(entry: NavBackStackEntry) {
    val argument = entry.arguments?.getString("repositoryName")
    val viewModel: RepositoryDetailViewModel = getViewModel {
        parametersOf(argument)
    }
    val uiState by viewModel.repositoryDetailUiStateFlow.collectAsState()

    val tagUiState by viewModel.repositoryTagUiStateFlow.collectAsState()

    when (uiState) {
        is RepositoryDetailUiState.Loading -> LoadingScreen()
        is RepositoryDetailUiState.Complete -> RepositoryDetailContent(
            uiState = uiState as RepositoryDetailUiState.Complete,
            tagUiState = tagUiState as RepositoryDetailUiState.Tag.Complete,
        )

        is RepositoryDetailUiState.Error -> return
        else -> return
    }
}

@Composable
fun RepositoryDetailContent(
    uiState: RepositoryDetailUiState.Complete,
    tagUiState: RepositoryDetailUiState.Tag.Complete,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = rememberLazyListState()
    ) {
        item {
            RepositoryHeader(uiState = uiState)
        }

        items(tagUiState.tags.count()) { index ->
            TagItem(tagName = tagUiState.tags[index].name, commitSha = tagUiState.tags[index].sha)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RepositoryHeader(uiState: RepositoryDetailUiState.Complete) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        uiState.uiModel.userName?.let { Text(text = it, modifier = Modifier.padding(start = 8.dp)) }

        Text(
            text = uiState.uiModel.repositoryName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp)
        )

        AsyncImage(model = uiState.uiModel.avatarUrl, contentDescription = null)

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
            Text(text = uiState.uiModel.forksCount, modifier = Modifier.padding(start = 4.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(text = uiState.uiModel.watchersCount, modifier = Modifier.padding(start = 4.dp))
        }
    }
}

@Composable
fun TagItem(tagName: String, commitSha: String) {
    Column {
        Text(text = "Tag: $tagName", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Commit SHA: $commitSha", style = MaterialTheme.typography.titleMedium)
    }
}


@Preview
@Composable
private fun TagItemPreview() {
    MaterialTheme {
        TagItem(tagName = "Hello world", commitSha = "fsakdjhfds-afdsf-sadfsdaf-fa")
    }
}
