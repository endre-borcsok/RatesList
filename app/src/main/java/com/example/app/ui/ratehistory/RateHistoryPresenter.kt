package com.example.app.ui.ratehistory

import com.example.app.R
import com.example.app.common.ForexSessionContract
import com.example.app.common.Utils
import com.example.app.ui.base.BasePresenter
import com.example.app.ui.rateslist.RatesListElement

class RateHistoryPresenter(private val forexSession: ForexSessionContract,
                           view: RateHistoryContract.View)
    : BasePresenter<RateHistoryContract.View>(view), RateHistoryContract.Presenter  {

    override fun getDailyRatesList(): MutableList<RateHistoryElement> {
        val symbol1 = forexSession.selectedSymbols
            .elementAtOrElse(0){ RatesListElement() }.symbol
        val symbol2 = forexSession.selectedSymbols
            .elementAtOrElse(1){ RatesListElement() }.symbol
        val list = ArrayList<RateHistoryElement>()

        val header = RateHistoryElement()
        header.date = getMvpView().getAppContext().getString(R.string.date)
        header.firstAmount = forexSession.selectedSymbols
            .elementAtOrElse(0){ RatesListElement() }.symbol
        header.secondAmount = forexSession.selectedSymbols
            .elementAtOrElse(1){ RatesListElement() }.symbol
        list.add(header)

        for (date in forexSession.dailyRates.keys) {
            val element = RateHistoryElement()
            element.date = date
            element.firstAmount = Utils.calculateRate(forexSession.currencyAmount,
                forexSession.dailyRates[date]?.get(symbol1)!!)
            element.secondAmount = Utils.calculateRate(forexSession.currencyAmount,
                forexSession.dailyRates[date]?.get(symbol2)!!)
            list.add(element)
        }
        return list
    }
}