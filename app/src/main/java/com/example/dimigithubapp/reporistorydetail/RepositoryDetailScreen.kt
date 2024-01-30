package com.example.dimigithubapp.reporistorydetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.dimigithubapp.compose.general.LoadingScreen
import com.example.dimigithubapp.model.RepositoryDetailUiState
import com.example.dimigithubapp.model.RepositoryTagUiModel
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
            uiState = uiState,
            tagUiState = tagUiState,
        )

        is RepositoryDetailUiState.Error -> return
        else -> return
    }
}

@Composable
fun RepositoryDetailContent(
    uiState: RepositoryDetailUiState,
    tagUiState: RepositoryDetailUiState.Tag,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        RepositoryHeader(uiState as RepositoryDetailUiState.Complete)
        if (tagUiState is RepositoryDetailUiState.Tag.Complete)
            TagList(tags = tagUiState.tags)
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
fun TagList(tags: List<RepositoryTagUiModel>) {
    Box(
        modifier = Modifier
            .background(color = Color.Gray)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(tags) { tag ->
                TagItem(tagName = tag.name, commitSha = tag.sha)
                Spacer(modifier = Modifier.height(8.dp))
            }
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
