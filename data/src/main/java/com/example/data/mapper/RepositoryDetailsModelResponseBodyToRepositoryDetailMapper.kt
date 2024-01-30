package com.example.data.mapper

import com.example.data.network.RepositoryDetailResponseBody
import com.example.domain.model.RepositoryDetailModel
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class RepositoryDetailsModelResponseBodyToRepositoryDetailMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<RepositoryDetailResponseBody, RepositoryDetailModel>(coroutineDispatcher) {

    override suspend fun RepositoryDetailResponseBody.toMappedEntity(): RepositoryDetailModel =
        RepositoryDetailModel(
            forksCount = forksCount,
            watchersCount = watcherCount,
            repositoryName = repositoryName,
            userName = user.name,
            avatarUrl = user.avatarUrl,
        )
}