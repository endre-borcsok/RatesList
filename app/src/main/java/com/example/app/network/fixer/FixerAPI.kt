package com.example.app.network.fixer

import com.example.app.network.fixer.model.FixerRatesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerAPI {
    @GET("/latest")
    fun getLatest(@Query("symbols") symbols: String): Observable<FixerRatesResponse>

    @GET("/{date}")
    fun getHistoricalRates(@Path("date") date: String,
                           @Query("base") base: String,
                           @Query("symbols") symbols: String): Observable<FixerRatesResponse>

    companion object {
        const val BASE_URL = "http://data.fixer.io/api/"
    }
}