package com.example.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRepositoryService {

    @GET("/users/octocat/repos")
    suspend fun getUserRepository(): Response<List<UserRepositoryResponseBody>>

    @GET("/repos/octocat/{repo}")
    suspend fun getRepositoryDetail(@Path("repo") repositoryName: String): Response<RepositoryDetailResponseBody>

    @GET("repos/octocat/{repo}/tags")
    suspend fun getRepositoryTags(@Path("repo") repositoryName: String): Response<List<RepositoryTagResponseBody>>
}