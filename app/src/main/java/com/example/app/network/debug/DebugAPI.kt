package com.example.app.network.debug

import com.example.app.network.debug.model.DebugRatesResponse
import io.reactivex.rxjava3.core.Observable

interface DebugAPI {
    fun getHistoricalRates(date: String, base: String, symbols: String)
    : Observable<DebugRatesResponse>
}