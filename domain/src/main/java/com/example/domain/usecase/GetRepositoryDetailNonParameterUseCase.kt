package com.example.domain.usecase

import com.example.domain.UserRepository
import com.example.domain.model.RepositoryDetailModel

class GetRepositoryDetailNonParameterUseCase(
    private val userRepository: UserRepository,
) : UseCase<String, RepositoryDetailModel> {

    override suspend fun execute(parameter: String): RepositoryDetailModel =
        userRepository.getRepositoryDetail(parameter)
}