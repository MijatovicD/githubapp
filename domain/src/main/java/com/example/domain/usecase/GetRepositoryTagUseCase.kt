package com.example.domain.usecase

import com.example.domain.UserRepository
import com.example.domain.model.RepositoryTagModel

class GetRepositoryTagUseCase(private val userRepository: UserRepository) :
    UseCase<String, List<RepositoryTagModel>> {

    override suspend fun execute(parameter: String): List<RepositoryTagModel> =
        userRepository.getRepositoryTag(parameter)
}