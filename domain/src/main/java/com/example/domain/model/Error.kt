package com.example.domain.model

sealed class Error : Throwable() {

    data object Connection : Error()

    data class Unknown(
        override val message: String? = null,
        override val cause: Throwable? = null,
    ) : Error()
}