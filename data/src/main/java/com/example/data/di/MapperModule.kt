package com.example.data.di

import com.example.data.mapper.RepositoryDetailsModelResponseBodyToRepositoryDetailMapper
import com.example.data.mapper.RepositoryTagResponseBodyToRepositoryTagModelMapper
import com.example.data.mapper.ThrowableToErrorMapper
import com.example.data.mapper.UserRepositoryResponseBodyToUserRepositoryMapper
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val mapperModule = module {

    factory { UserRepositoryResponseBodyToUserRepositoryMapper(Dispatchers.Default) }
    factory { RepositoryDetailsModelResponseBodyToRepositoryDetailMapper(Dispatchers.Default) }
    factory { RepositoryTagResponseBodyToRepositoryTagModelMapper(Dispatchers.Default) }
    factory { ThrowableToErrorMapper(Dispatchers.Default) }
}