package com.example.dimigithubapp.mapper

import com.example.dimigithubapp.model.RepositoryDetailUiModel
import com.example.domain.model.RepositoryDetailModel
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class RepositoryDetailToRepositoryDetailUiModelMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<RepositoryDetailModel, RepositoryDetailUiModel>(defaultDispatcher = coroutineDispatcher) {

    override suspend fun RepositoryDetailModel.toMappedEntity(): RepositoryDetailUiModel =
        RepositoryDetailUiModel(
            userName = userName,
            avatarUrl = avatarUrl,
            watchersCount = watchersCount.toString(),
            forksCount = forksCount.toString(),
            repositoryName = repositoryName,
        )
}