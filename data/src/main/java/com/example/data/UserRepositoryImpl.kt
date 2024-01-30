package com.example.data

import com.example.data.mapper.RepositoryDetailsModelResponseBodyToRepositoryDetailMapper
import com.example.data.mapper.RepositoryTagResponseBodyToRepositoryTagModelMapper
import com.example.data.mapper.ThrowableToErrorMapper
import com.example.data.mapper.UserRepositoryResponseBodyToUserRepositoryMapper
import com.example.data.network.UserRepositoryService
import com.example.domain.UserRepository
import com.example.domain.model.RepositoryDetailModel
import com.example.domain.model.RepositoryTagModel
import com.example.domain.model.UserRepositoryModel

class UserRepositoryImpl(
    private val userRepositoryService: UserRepositoryService,
    private val userRepositoryResponseBodyToUserRepositoryMapper: UserRepositoryResponseBodyToUserRepositoryMapper,
    private val repositoryDetailMapper: RepositoryDetailsModelResponseBodyToRepositoryDetailMapper,
    private val repositoryTagModelMapper: RepositoryTagResponseBodyToRepositoryTagModelMapper,
    private val throwableToErrorMapper: ThrowableToErrorMapper,
) : UserRepository {

    override suspend fun getUserRepository(): List<UserRepositoryModel> =
        userRepositoryService.runCatching {
            getUserRepository()
        }.mapCatching {
            requireNotNull(it.body())
        }.mapCatching {
            userRepositoryResponseBodyToUserRepositoryMapper.map(it)
        }.getOrThrowError(throwableToErrorMapper)

    override suspend fun getRepositoryDetail(repositoryName: String): RepositoryDetailModel =
        userRepositoryService.runCatching {
            getRepositoryDetail(repositoryName)
        }.mapCatching {
            requireNotNull(it.body())
        }.mapCatching {
            repositoryDetailMapper.map(it)
        }.getOrThrowError(throwableToErrorMapper)

    override suspend fun getRepositoryTag(repositoryName: String): List<RepositoryTagModel> =
        userRepositoryService.runCatching {
            getRepositoryTags(repositoryName)
        }.mapCatching {
            requireNotNull(it.body())
        }.mapCatching {
            repositoryTagModelMapper.map(it)
        }.getOrThrowError(throwableToErrorMapper)

    private suspend fun <T> Result<T>.getOrThrowError(throwableToErrorMapper: ThrowableToErrorMapper): T =
        getOrElse { throw throwableToErrorMapper.map(it) }
}