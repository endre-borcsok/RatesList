package com.example.app.ui.rateslist

import com.example.app.ui.base.BaseContract

interface RatesListContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getLatestRatesList(): MutableList<RatesListElement>
        fun loadRateHistory(fromDate: String)
        fun refreshLatestRatesList()
        fun selectRatesListItem(item: RatesListElement)
    }

    interface View : BaseContract.View {
        fun showRateHistory()
        fun refreshList()
        fun refreshListItem(item: RatesListElement)
        fun onError()
    }
}