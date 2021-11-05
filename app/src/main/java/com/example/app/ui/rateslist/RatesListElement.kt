package com.example.app.ui.rateslist


class RatesListElement {
    var symbol = ""
    var amount = ""
    var selected: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other !is RatesListElement) return false
        val otherElement: RatesListElement = other
        return otherElement.symbol == symbol && otherElement.amount == amount
    }

    override fun hashCode(): Int {
        var hashCode = 17
        hashCode = 31 * hashCode + symbol.hashCode()
        hashCode = 31 * hashCode + amount.hashCode()
        return hashCode
    }
}