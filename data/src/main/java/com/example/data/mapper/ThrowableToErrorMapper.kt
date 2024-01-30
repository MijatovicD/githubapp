package com.example.data.mapper

import com.example.domain.model.Error
import com.example.domain.usecase.SuspendingMapper
import kotlinx.coroutines.CoroutineDispatcher
import java.net.UnknownHostException

class ThrowableToErrorMapper(coroutineDispatcher: CoroutineDispatcher) :
    SuspendingMapper<Throwable, Error>(coroutineDispatcher) {

    override suspend fun Throwable.toMappedEntity(): Error =
        when (this) {
            is UnknownHostException -> Error.Connection
            else -> Error.Unknown(message, this)
        }
}