package com.example.dimigithubapp.mapper

import com.example.dimigithubapp.model.UserRepositoryUiState
import com.example.domain.model.Error
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class ThrowableToUserRepositoryErrorUiStateMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<Throwable, UserRepositoryUiState.Error>(coroutineDispatcher) {

    override suspend fun Throwable.toMappedEntity(): UserRepositoryUiState.Error =
        when (this) {
            Error.Connection -> UserRepositoryUiState.Error.Connection
            else -> UserRepositoryUiState.Error.Unknown
        }
}