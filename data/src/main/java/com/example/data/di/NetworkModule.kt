package com.example.data.di

import com.example.data.network.ServiceProvider
import com.example.data.network.UserRepositoryService
import org.koin.dsl.module

val networkModule = module {
    single<UserRepositoryService> { ServiceProvider.create().create(UserRepositoryService::class.java) }
    single { ServiceProvider.create() }
}