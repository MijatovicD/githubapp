package com.example.domain.usecase

interface NonParameterUseCase<out UseCaseResult> {
    suspend fun execute(): UseCaseResult
}