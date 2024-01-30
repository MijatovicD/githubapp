package com.example.domain.model

data class RepositoryDetailModel(
    val userName: String?,
    val avatarUrl: String?,
    val watchersCount: Int,
    val forksCount: Int,
    val repositoryName: String,
)