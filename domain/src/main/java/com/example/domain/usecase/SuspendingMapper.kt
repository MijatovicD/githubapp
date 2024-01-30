package com.example.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendingMapper<in T, out R>(
    private val defaultDispatcher: CoroutineDispatcher,
) {
    abstract suspend fun T.toMappedEntity(): R

    private suspend fun T.toMappedEntityOrNull(onFailureBlock: OnFailureBlock?): R? =
        runCatching {
            toMappedEntity()
        }.onFailure { throwable ->
            onFailureBlock?.invoke(getInformationForFailureBlock(), throwable)
        }.getOrNull()

    suspend fun map(model: T): R =
        withContext(defaultDispatcher) {
            model.toMappedEntity()
        }

    private suspend fun mapCollection(model: Collection<T>): Collection<R> =
        withContext(defaultDispatcher) {
            model.map { it.toMappedEntity() }
        }

    suspend fun mapList(model: List<T>): List<R> =
        mapCollection(model).toList()

    private fun T.getInformationForFailureBlock(): String =
        MODEL_STRING_PATTERN.format(this?.let { it::class.simpleName }, toString())

    private companion object {
        const val MODEL_STRING_PATTERN = "%s: %s"
    }
}

private typealias OnFailureBlock = (modelInformation: String, throwable: Throwable) -> Unit
