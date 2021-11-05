package com.example.app.network

import com.example.app.network.debug.DebugAPI
import com.example.app.network.debug.DebugApiModule
import com.example.app.network.fixer.FixerAPI
import com.example.app.network.fixer.FixerApiModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [FixerApiModule::class, DebugApiModule::class])
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideNetworkManager(fixerApi: FixerAPI,
                                       debugAPI: DebugAPI): NetworkManagerContract {
        return NetworkManager(fixerApi, debugAPI)
    }
}