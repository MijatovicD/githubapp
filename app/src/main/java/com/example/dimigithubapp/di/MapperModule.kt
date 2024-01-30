package com.example.dimigithubapp.di

import com.example.dimigithubapp.mapper.RepositoryDetailToRepositoryDetailUiModelMapper
import com.example.dimigithubapp.mapper.RepositoryTagModelToRepositoryTagUiModelMapper
import com.example.dimigithubapp.mapper.ThrowableToUserRepositoryErrorUiStateMapper
import com.example.dimigithubapp.mapper.UserRepositoryToUserRepositoryUiModelMapper
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val mapperModule = module {
    factory { UserRepositoryToUserRepositoryUiModelMapper(Dispatchers.IO) }
    factory { RepositoryDetailToRepositoryDetailUiModelMapper(Dispatchers.IO) }
    factory { RepositoryTagModelToRepositoryTagUiModelMapper(Dispatchers.IO) }
    factory { ThrowableToUserRepositoryErrorUiStateMapper(Dispatchers.IO) }
}