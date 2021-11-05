package com.example.app.network.debug

import com.example.app.network.debug.model.DebugRatesResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable

class DebugApiManager : DebugAPI {
    companion object {
        const val json = "{\n" +
                "  \"success\":true,\n" +
                "  \"timestamp\":1633969504,\n" +
                "  \"baseCurrency\":\"EUR\",\n" +
                "  \"today\":\"2021-10-15\",\n" +
                "  \"dailyRates\":{\n" +
                "    \"USD\":1.156785,\n" +
                "    \"AUD\":1.571087,\n" +
                "    \"CAD\":1.442221,\n" +
                "    \"PLN\":4.584396,\n" +
                "    \"MXN\":24.056324\n" +
                "  }\n" +
                "}"
    }

    override fun getHistoricalRates(
        date: String,
        base: String,
        symbols: String
    ): Observable<DebugRatesResponse> {
        return Observable.just(Gson().fromJson(json, DebugRatesResponse::class.java))
    }
}