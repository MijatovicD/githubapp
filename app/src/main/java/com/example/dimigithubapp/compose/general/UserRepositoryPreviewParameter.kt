package com.example.dimigithubapp.compose.general

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.dimigithubapp.model.UserRepositoryUiModel
import kotlin.random.Random

class UserRepositoryPreviewParameter : PreviewParameterProvider<List<UserRepositoryUiModel>> {
    override val values: Sequence<List<UserRepositoryUiModel>> =
        sequenceOf(generateUserRepositoryUiModel())

    private fun generateUserRepositoryUiModel(): List<UserRepositoryUiModel> =
        listOf(
            UserRepositoryUiModel(
                id = Random.hashCode().toString(),
                repositoryName = "Test",
                openIssueCount = Random.nextInt()
            )
        )
}