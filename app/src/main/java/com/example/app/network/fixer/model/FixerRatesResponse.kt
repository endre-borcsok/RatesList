package com.example.app.network.fixer.model

import com.example.app.network.model.RatesEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FixerRatesResponse (
    @JsonProperty("success")
    override var success: Boolean = false,
    @JsonProperty("base")
    override var base: String = "",
    @JsonProperty("date")
    override var date: String = "",
    @JsonProperty("rates")
    override var rates: Map<String, Float> = HashMap()
) : RatesEntity