package com.example.app.network.debug

import dagger.Module
import dagger.Provides

@Module
class DebugApiModule {
    @Provides
    fun provideDebugApi(): DebugAPI {
        return DebugApiManager()
    }
}