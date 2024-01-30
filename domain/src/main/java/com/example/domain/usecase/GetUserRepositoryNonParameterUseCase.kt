package com.example.domain.usecase

import com.example.domain.UserRepository
import com.example.domain.model.UserRepositoryModel

class GetUserRepositoryNonParameterUseCase(private val userRepository: UserRepository) :
    NonParameterUseCase<List<UserRepositoryModel>> {

    override suspend fun execute(): List<UserRepositoryModel> =
        userRepository.getUserRepository()
}