package com.example.data.di

import com.example.data.UserRepositoryImpl
import com.example.domain.UserRepository
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}