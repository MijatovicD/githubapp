package com.example.dimigithubapp.mapper

import com.example.dimigithubapp.model.UserRepositoryUiModel
import com.example.domain.model.UserRepositoryModel
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class UserRepositoryToUserRepositoryUiModelMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<UserRepositoryModel, UserRepositoryUiModel>(coroutineDispatcher) {

    override suspend fun UserRepositoryModel.toMappedEntity(): UserRepositoryUiModel =
        UserRepositoryUiModel(
            id = id,
            repositoryName = name,
            openIssueCount = openIssueCount,
        )
}