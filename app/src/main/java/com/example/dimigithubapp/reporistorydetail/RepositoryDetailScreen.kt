package com.example.dimigithubapp.reporistorydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.example.dimigithubapp.R
import com.example.dimigithubapp.compose.general.EmptyListContent
import com.example.dimigithubapp.compose.general.LoadingScreen
import com.example.dimigithubapp.compose.general.RepositoryDetailPreviewParameter
import com.example.dimigithubapp.compose.general.TopBar
import com.example.dimigithubapp.compose.general.UnknownErrorScreen
import com.example.dimigithubapp.model.RepositoryDetailUiState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RepositoryDetailScreen(entry: NavBackStackEntry, onBackClick: () -> Unit) {
    val argument: String? = entry.arguments?.getString("repositoryName")
    val viewModel: RepositoryDetailViewModel = getViewModel {
        parametersOf(argument)
    }
    val uiState: RepositoryDetailUiState by viewModel.repositoryDetailUiStateFlow.collectAsState()

    val tagUiState: RepositoryDetailUiState.Tag by viewModel.repositoryTagUiStateFlow.collectAsState()

    when (uiState) {
        is RepositoryDetailUiState.Loading -> LoadingScreen()
        is RepositoryDetailUiState.Complete -> RepositoryDetailContent(
            uiState = uiState as RepositoryDetailUiState.Complete,
            tagUiState = tagUiState,
            onBackClick = onBackClick,
        )

        is RepositoryDetailUiState.Error -> UnknownErrorScreen()
        else -> return
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailContent(
    uiState: RepositoryDetailUiState.Complete,
    tagUiState: RepositoryDetailUiState.Tag,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(
                    id = R.string.topbar_repository_detail,
                    uiState.uiModel.repositoryName
                ),
                showBackButton = true,
                onBackClick = onBackClick,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues),
            state = rememberLazyListState(),
        ) {
            item { RepositoryHeader(uiState = uiState) }

            if (tagUiState is RepositoryDetailUiState.Tag.Complete)
                itemsIndexed(tagUiState.tags) { _, tag ->
                    TagItem(
                        tagName = tag.name,
                        commitSha = tag.sha,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
        }
    }

    when (tagUiState) {
        is RepositoryDetailUiState.Tag.EmptyList -> EmptyListContent(R.string.repository_details_tag_blank_screen)
        is RepositoryDetailUiState.Tag.Loading -> LoadingScreen()
        else -> return
    }
}

@Composable
fun RepositoryHeader(uiState: RepositoryDetailUiState.Complete, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = uiState.uiModel.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(MaterialTheme.shapes.medium),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = uiState.uiModel.repositoryName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
                Text(
                    text = uiState.uiModel.forksCount,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 4.dp),
                )
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Text(
                    text = uiState.uiModel.watchersCount,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }
    }
}

@Composable
fun TagItem(tagName: String, commitSha: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.repository_details_tag_title, tagName),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )
            Text(
                text = stringResource(id = R.string.repository_details_commit_sha_title, commitSha),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
            )
        }
    }

}

@Preview
@Composable
private fun RepositoryDetailHeaderPreview(
    @PreviewParameter(RepositoryDetailPreviewParameter::class) repositoryDetailCompleteState: RepositoryDetailUiState.Complete,
) {
    MaterialTheme {
        RepositoryHeader(uiState = repositoryDetailCompleteState)
    }
}


@Preview
@Composable
private fun TagItemPreview() {
    MaterialTheme {
        TagItem(tagName = "Hello world", commitSha = "fsakdjhfds-afdsf-sadfsdaf-fa")
    }
}
