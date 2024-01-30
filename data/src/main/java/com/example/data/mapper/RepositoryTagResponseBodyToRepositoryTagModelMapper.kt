package com.example.data.mapper

import com.example.data.network.RepositoryTagResponseBody
import com.example.domain.model.RepositoryTagModel
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class RepositoryTagResponseBodyToRepositoryTagModelMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<List<RepositoryTagResponseBody>, List<RepositoryTagModel>>(defaultDispatcher = coroutineDispatcher) {

    override suspend fun List<RepositoryTagResponseBody>.toMappedEntity(): List<RepositoryTagModel> =
        map { tag ->
            RepositoryTagModel(
                name = tag.name,
                sha = tag.commit.sha,
            )
        }
}