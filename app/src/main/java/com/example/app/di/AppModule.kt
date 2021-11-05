package com.example.app.di

import android.app.Application
import android.content.Context
import com.example.app.common.ForexSession
import com.example.app.common.ForexSessionContract
import com.example.app.common.Utils
import com.example.app.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideForexSession(): ForexSessionContract {
        val forexSession = ForexSession()
        Utils.initForexSession(forexSession)
        return forexSession
    }
}