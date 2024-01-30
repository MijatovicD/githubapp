package com.example.dimigithubapp.model

sealed class RepositoryDetailUiState {

    data object Loading : RepositoryDetailUiState()

    data class Complete(val uiModel: RepositoryDetailUiModel) : RepositoryDetailUiState()

    data object Error : RepositoryDetailUiState()

    sealed class Tag : RepositoryDetailUiState() {

        data object Loading : Tag()

        data class Complete(val tags: List<RepositoryTagUiModel>) : Tag()

        data object Error : Tag()

    }
}