package com.example.dimigithubapp.reporistorydetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimigithubapp.mapper.RepositoryDetailToRepositoryDetailUiModelMapper
import com.example.dimigithubapp.mapper.RepositoryTagModelToRepositoryTagUiModelMapper
import com.example.dimigithubapp.model.RepositoryDetailUiModel
import com.example.dimigithubapp.model.RepositoryDetailUiState
import com.example.dimigithubapp.model.RepositoryTagUiModel
import com.example.domain.model.RepositoryDetailModel
import com.example.domain.model.RepositoryTagModel
import com.example.domain.usecase.GetRepositoryDetailNonParameterUseCase
import com.example.domain.usecase.GetRepositoryTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryDetailViewModel(
    private val repositoryName: String,
    private val getRepositoryDetailUseCase: GetRepositoryDetailNonParameterUseCase,
    private val getRepositoryTagUseCase: GetRepositoryTagUseCase,
    private val repositoryDetailToRepositoryDetailUiModelMapper: RepositoryDetailToRepositoryDetailUiModelMapper,
    private val repositoryTagUiModelMapper: RepositoryTagModelToRepositoryTagUiModelMapper,
) : ViewModel() {

    private val mutableStateRepositoryDetail: MutableStateFlow<RepositoryDetailUiState> =
        MutableStateFlow(RepositoryDetailUiState.Loading)

    val repositoryDetailUiStateFlow: StateFlow<RepositoryDetailUiState> =
        mutableStateRepositoryDetail.asStateFlow()

    private val mutableStateRepositoryTag: MutableStateFlow<RepositoryDetailUiState.Tag> =
        MutableStateFlow(RepositoryDetailUiState.Tag.Loading)

    val repositoryTagUiStateFlow: StateFlow<RepositoryDetailUiState.Tag> =
        mutableStateRepositoryTag.asStateFlow()

    init {
        getRepositoryDetail()
        getRepositoryTags()
    }

    private fun getRepositoryDetail() {
        viewModelScope.launch {
            getRepositoryDetailUseCase.runCatching {
                execute(repositoryName)
            }.mapCatching {
                RepositoryDetailUiState.Complete(it.toUiModel())
            }.getOrElse {
                RepositoryDetailUiState.Error
            }.also { uiState ->
                mutableStateRepositoryDetail.value = uiState
            }
        }
    }

    private fun getRepositoryTags() {
        viewModelScope.launch {
            getRepositoryTagUseCase.runCatching {
                execute(repositoryName)
            }.mapCatching {
                RepositoryDetailUiState.Tag.Complete(it.toUiModel())
            }.getOrElse {
                RepositoryDetailUiState.Tag.Error
            }.also { uiState ->
                mutableStateRepositoryTag.value = uiState
            }
        }
    }

    private suspend fun RepositoryDetailModel.toUiModel(): RepositoryDetailUiModel =
        repositoryDetailToRepositoryDetailUiModelMapper.runCatching {
            map(this@toUiModel)
        }.onFailure {
            Log.d("00>", "Couldn't map to repository detail ui model.")
        }.getOrThrow()

    private suspend fun List<RepositoryTagModel>.toUiModel(): List<RepositoryTagUiModel> =
        repositoryTagUiModelMapper.runCatching {
            map(this@toUiModel)
        }.onFailure {
            Log.d("00>", "Couldn't map to repository tags ui model.")
        }.getOrThrow()
}