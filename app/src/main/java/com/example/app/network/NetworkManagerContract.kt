package com.example.app.network

import com.example.app.network.model.RatesEntity
import io.reactivex.rxjava3.core.Observable

interface NetworkManagerContract {
    fun getHistoricalRates(date: String, base: String, symbols: String):
            Observable<RatesEntity>
}