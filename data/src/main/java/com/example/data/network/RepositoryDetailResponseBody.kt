package com.example.data.network

import com.google.gson.annotations.SerializedName

data class RepositoryDetailResponseBody(
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("watchers_count") val watcherCount: Int,
    @SerializedName("name") val repositoryName: String,
    @SerializedName("owner") val user: UserResponseBody,
) {
    data class UserResponseBody(
        @SerializedName("name") val name: String?,
        @SerializedName("avatar_url") val avatarUrl: String?,
    )
}