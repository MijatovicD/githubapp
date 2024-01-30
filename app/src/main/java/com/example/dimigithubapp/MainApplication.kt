package com.example.dimigithubapp

import android.app.Application
import com.example.di.dataQualifier
import com.example.di.dependencyInjectionModule
import com.example.dimigithubapp.di.appModule
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule.plus(dependencyInjectionModule))
            val dataModules: List<Module> by this@MainApplication.inject(dataQualifier)
            koin.loadModules(dataModules)
        }
    }
}