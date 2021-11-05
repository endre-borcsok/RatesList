package com.example.app.common

import com.example.app.ui.rateslist.RatesListElement

interface ForexSessionContract {
    val baseCurrency: String
    var currencyAmount: Float
    val dailyRates: MutableMap<String, MutableMap<String, Float>>
    val selectedSymbols: MutableList<RatesListElement>
    fun selectionLimitReached(): Boolean
    fun getNextDateString(fromDate: String): String
}