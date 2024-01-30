package com.example.domain.di

import com.example.domain.usecase.GetRepositoryDetailNonParameterUseCase
import com.example.domain.usecase.GetRepositoryTagUseCase
import com.example.domain.usecase.GetUserRepositoryNonParameterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUserRepositoryNonParameterUseCase(get()) }
    factory { GetRepositoryDetailNonParameterUseCase(get()) }
    factory { GetRepositoryTagUseCase(get()) }
}