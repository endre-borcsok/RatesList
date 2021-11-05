package com.example.app.common

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun calculateRate(amount: Float, rate: Float): String {
        return String.format("%.2f", amount * rate)
    }

    fun getAllSymbols(): String {
        val currencies = Symbols.values()
        return currencies.joinToString (separator = ",") { it.symbol }
    }

    fun getTodaysDate(): String {
        val dateFormat = SimpleDateFormat(ForexSession.DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        val date = calendar.time
        return dateFormat.format(date)
    }

    fun initForexSession(forexSession: ForexSessionContract) {
        val dateFormat = SimpleDateFormat(ForexSession.DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(ForexSession.MAX_NUMBER_OF_DAYS) {
            val date = calendar.time
            forexSession.dailyRates[dateFormat.format(date)] = HashMap()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
    }
}