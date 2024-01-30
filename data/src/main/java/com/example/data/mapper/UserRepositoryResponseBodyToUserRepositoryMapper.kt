package com.example.data.mapper

import com.example.data.network.UserRepositoryResponseBody
import com.example.domain.usecase.SuspendingMapper
import com.example.domain.model.UserRepositoryModel
import kotlinx.coroutines.CoroutineDispatcher

class UserRepositoryResponseBodyToUserRepositoryMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<List<UserRepositoryResponseBody>, List<UserRepositoryModel>>(coroutineDispatcher) {

    override suspend fun List<UserRepositoryResponseBody>.toMappedEntity(): List<UserRepositoryModel> =
        map {
            UserRepositoryModel(
                id = it.id,
                name = it.name,
                openIssueCount = it.openIssueCount,
            )
        }
}