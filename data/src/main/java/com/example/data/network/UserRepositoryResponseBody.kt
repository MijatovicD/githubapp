package com.example.data.network

import com.google.gson.annotations.SerializedName

data class UserRepositoryResponseBody(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("open_issues_count") val openIssueCount: Int,
)