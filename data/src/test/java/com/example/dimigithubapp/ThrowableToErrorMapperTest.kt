package com.example.dimigithubapp

import com.example.data.mapper.ThrowableToErrorMapper
import com.example.domain.model.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.UnknownHostException
import kotlin.test.assertEquals

class ThrowableToErrorMapperTest {

    private val throwableToErrorMapper = ThrowableToErrorMapper(Dispatchers.Unconfined)

    @Test
    fun `given Connection Error When mapping Then expected is equal to actual`() {
        val expected = Error.Connection
        val exception =  UnknownHostException("No address associated with hostname")

        runBlocking {
            val actual = throwableToErrorMapper.map(exception)
            assertEquals(expected, actual)
        }
    }


    @Test
    fun `given Unknown Error When mapping Then expected is equal to actual`() {
        val exception =  IllegalArgumentException()
        val expected = Error.Unknown(exception.message, exception)

        runBlocking {
            val actual = throwableToErrorMapper.map(exception)
            assertEquals(expected, actual)
        }
    }
}