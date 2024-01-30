package com.example.dimigithubapp.model

sealed class UserRepositoryUiState {

    data object Loading : UserRepositoryUiState()

    data class Complete(val uiModel: List<UserRepositoryUiModel>) : UserRepositoryUiState()

    data object EmptyList : UserRepositoryUiState()

    sealed class Error : UserRepositoryUiState() {

        data object Connection : Error()

        data object Unknown : Error()
    }
}