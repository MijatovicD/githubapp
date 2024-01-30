package com.example.data.network

import com.google.gson.annotations.SerializedName

data class RepositoryTagResponseBody(
    @SerializedName("name") val name: String,
    @SerializedName("commit") val commit: Sha,
) {
    data class Sha(
        @SerializedName("sha") val sha: String
    )
}