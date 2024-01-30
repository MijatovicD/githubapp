package com.example.dimigithubapp.di

import com.example.dimigithubapp.reporistorydetail.RepositoryDetailViewModel
import com.example.dimigithubapp.userrepository.UserRepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserRepositoryViewModel(get(), get(), get()) }
    viewModel { (repositoryName: String) ->
        RepositoryDetailViewModel(
            repositoryName,
            get(),
            get(),
            get(),
            get(),
        )
    }
}