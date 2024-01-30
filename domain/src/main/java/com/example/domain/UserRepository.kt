package com.example.domain

import com.example.domain.model.RepositoryDetailModel
import com.example.domain.model.RepositoryTagModel
import com.example.domain.model.UserRepositoryModel

interface UserRepository {

    suspend fun getUserRepository(): List<UserRepositoryModel>

    suspend fun getRepositoryDetail(repositoryName: String): RepositoryDetailModel

    suspend fun getRepositoryTag(repositoryName: String): List<RepositoryTagModel>
}