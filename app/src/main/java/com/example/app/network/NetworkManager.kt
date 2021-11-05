package com.example.app.network

import com.example.app.network.debug.DebugAPI
import com.example.app.network.fixer.FixerAPI
import com.example.app.network.model.RatesEntity
import io.reactivex.rxjava3.core.Observable

class NetworkManager(private val fixerApi: FixerAPI,
                     private val debugApi: DebugAPI) : NetworkManagerContract {
    override fun getHistoricalRates(date: String, base: String, symbols: String):
            Observable<RatesEntity> {
        return fixerApi.getHistoricalRates(date, base, symbols) as Observable<RatesEntity>
    }
}