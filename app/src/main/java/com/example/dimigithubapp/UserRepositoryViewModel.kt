package com.example.dimigithubapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimigithubapp.mapper.ThrowableToUserRepositoryErrorUiStateMapper
import com.example.dimigithubapp.mapper.UserRepositoryToUserRepositoryUiModelMapper
import com.example.dimigithubapp.model.UserRepositoryUiModel
import com.example.dimigithubapp.model.UserRepositoryUiState
import com.example.domain.model.UserRepositoryModel
import com.example.domain.usecase.GetUserRepositoryNonParameterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserRepositoryViewModel(
    private val userRepositoryUseCase: GetUserRepositoryNonParameterUseCase,
    private val userRepositoryToUserRepositoryUiModelMapper: UserRepositoryToUserRepositoryUiModelMapper,
    private val userRepositoryErrorUiStateMapper: ThrowableToUserRepositoryErrorUiStateMapper,
) : ViewModel() {

    private val mutableStateUserRepository: MutableStateFlow<UserRepositoryUiState> =
        MutableStateFlow(UserRepositoryUiState.Loading)

    val userRepositoryListUiStateFlow: StateFlow<UserRepositoryUiState> =
        mutableStateUserRepository.asStateFlow()

    init {
        getUserRepositories()
    }

    private fun getUserRepositories() {
        viewModelScope.launch {
            userRepositoryUseCase.runCatching {
                execute()
            }.mapCatching { list ->
                if (list.isEmpty()) UserRepositoryUiState.EmptyList
                else UserRepositoryUiState.Complete(list.toUiModel())
            }.getOrElse {
                it.toErrorUiState()
            }.also { uiState ->
                mutableStateUserRepository.value = uiState
            }
        }
    }

    private suspend fun List<UserRepositoryModel>.toUiModel(): List<UserRepositoryUiModel> =
        userRepositoryToUserRepositoryUiModelMapper.runCatching {
            mapList(this@toUiModel)
        }.onFailure {
            Log.d("00>", "Couldn't map to user repository ui model.")
        }.getOrThrow()

    private suspend fun Throwable.toErrorUiState(): UserRepositoryUiState.Error =
        userRepositoryErrorUiStateMapper.runCatching {
            map(this@toErrorUiState)
        }.getOrDefault(UserRepositoryUiState.Error.Unknown)
}