package com.example.app.network.debug.model

import com.example.app.network.model.RatesEntity
import com.google.gson.annotations.SerializedName

data class DebugRatesResponse (
    @SerializedName("success")
    override var success: Boolean = false,
    @SerializedName("baseCurrency")
    override var base: String = "",
    @SerializedName("today")
    override var date: String = "",
    @SerializedName("dailyRates")
    override var rates: Map<String, Float> = HashMap()
) : RatesEntity