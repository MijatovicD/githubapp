package com.example.dimigithubapp.mapper

import com.example.dimigithubapp.model.RepositoryTagUiModel
import com.example.domain.model.RepositoryTagModel
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher

class RepositoryTagModelToRepositoryTagUiModelMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<List<RepositoryTagModel>, List<RepositoryTagUiModel>>(coroutineDispatcher) {

    override suspend fun List<RepositoryTagModel>.toMappedEntity(): List<RepositoryTagUiModel> =
        map { tag ->
            RepositoryTagUiModel(
                name = tag.name,
                sha = tag.sha,
            )
        }
}