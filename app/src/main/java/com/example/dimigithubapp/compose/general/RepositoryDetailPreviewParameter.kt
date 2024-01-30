package com.example.dimigithubapp.compose.general

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.dimigithubapp.model.RepositoryDetailUiModel
import com.example.dimigithubapp.model.RepositoryDetailUiState

class RepositoryDetailPreviewParameter : PreviewParameterProvider<RepositoryDetailUiState> {

    override val values: Sequence<RepositoryDetailUiState> =
        sequenceOf(generateRepositoryDetailCompleteUiState())

    private fun generateRepositoryDetailCompleteUiState(): RepositoryDetailUiState.Complete =
        RepositoryDetailUiState.Complete(
            uiModel = RepositoryDetailUiModel(
                userName = "Dimi",
                avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
                watchersCount = "123",
                forksCount = "567",
                repositoryName = "Awesome repository"
            )
        )
}