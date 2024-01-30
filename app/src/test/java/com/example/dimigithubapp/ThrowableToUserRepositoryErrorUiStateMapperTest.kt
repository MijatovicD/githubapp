package com.example.dimigithubapp

import com.example.dimigithubapp.mapper.ThrowableToUserRepositoryErrorUiStateMapper
import com.example.dimigithubapp.model.UserRepositoryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class ThrowableToUserRepositoryErrorUiStateMapperTest {

    private val throwableToErrorMapper = ThrowableToUserRepositoryErrorUiStateMapper(Dispatchers.Unconfined)

    @Test
    fun `given Connection Error When mapping Then expected is equal to actual`() {
        val expected = UserRepositoryUiState.Error.Connection
        val exception = UnknownHostException("No address associated with hostname")

        runBlocking {
            val actual = throwableToErrorMapper.map(exception)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `given Unknown Error When mapping Then expected is equal to actual`() {
        val exception = IllegalArgumentException()
        val expected = UserRepositoryUiState.Error.Unknown

        runBlocking {
            val actual = throwableToErrorMapper.map(exception)
            assertEquals(expected, actual)
        }
    }
}