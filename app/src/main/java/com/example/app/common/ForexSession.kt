package com.example.app.common

import com.example.app.ui.rateslist.RatesListElement
import java.util.*
import kotlin.collections.ArrayList

class ForexSession : ForexSessionContract {
    companion object {
        const val MAX_NUMBER_OF_DAYS = 5
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val MAX_NUMBER_OF_SELECTED_ITEMS = 2
    }

    override val baseCurrency = Symbols.EUR.symbol
    override var currencyAmount: Float = 1.0f
    override val dailyRates: MutableMap<String, MutableMap<String, Float>> = LinkedHashMap()
    override val selectedSymbols: MutableList<RatesListElement> = ArrayList()

    override fun selectionLimitReached(): Boolean {
        return selectedSymbols.size >= MAX_NUMBER_OF_SELECTED_ITEMS
    }

    override fun getNextDateString(fromDate: String): String {
        var foundFromDate = false
        for (k in dailyRates.keys) {
            if (foundFromDate) return k
            if (k == fromDate) foundFromDate = true
        }
        return String()
    }
}