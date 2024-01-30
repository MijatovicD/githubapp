package com.example.dimigithubapp.di

import com.example.domain.di.useCaseModule

val appModule = listOf(
    viewModelModule,
    useCaseModule,
    mapperModule,
)
