package com.example.app.ui.rateslist

import com.example.app.common.ForexSessionContract
import com.example.app.common.Utils
import com.example.app.network.NetworkManagerContract
import com.example.app.ui.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RatesListPresenter(private val networkManager: NetworkManagerContract,
                         private val forexSession: ForexSessionContract,
                         view: RatesListContract.View) :
    BasePresenter<RatesListContract.View>(view), RatesListContract.Presenter {

    private val latestRatesListElements: MutableList<RatesListElement> = ArrayList()

    override fun getLatestRatesList(): MutableList<RatesListElement> {
        return latestRatesListElements
    }

    override fun loadRateHistory(fromDate: String) {
        if (!canLoadDate(fromDate)) return
        addDisposable(networkManager.getHistoricalRates(fromDate, forexSession.baseCurrency,
            Utils.getAllSymbols())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getMvpView().showLoadingDialog() }
            .doOnError { error() }
            .subscribe ({ response ->
                if (response.success) {
                    forexSession.dailyRates[response.date]?.putAll(response.rates)
                    loadRateHistory(forexSession.getNextDateString(fromDate))
                } else
                    error()
                }, { error() }))
    }

    private fun error() {
        getMvpView().onError()
        getMvpView().hideLoadingDialog()
    }

    private fun canLoadDate(fromDate: String): Boolean {
        return if (!forexSession.dailyRates.containsKey(fromDate)) {
            getMvpView().hideLoadingDialog()
            refreshLatestRatesList()
            false
        } else if (forexSession.dailyRates[fromDate]?.isNotEmpty()!!) {
            loadRateHistory(forexSession.getNextDateString(fromDate))
            false
        } else
            true
    }

    override fun refreshLatestRatesList() {
        getLatestRatesList().clear()
        for (key in forexSession.dailyRates[Utils.getTodaysDate()]?.keys!!) {
            val rate = forexSession.dailyRates[Utils.getTodaysDate()]?.get(key)
            val latestRate = RatesListElement()
            latestRate.symbol = key
            latestRate.amount = Utils.calculateRate(forexSession.currencyAmount, rate!!)
            latestRate.selected = forexSession.selectedSymbols.contains(latestRate)
            getLatestRatesList().add(latestRate)
            getMvpView().refreshListItem(latestRate)
        }
    }

    override fun selectRatesListItem(item: RatesListElement) {
        item.selected = !item.selected
        if (item.selected) {
            if (forexSession.selectionLimitReached()) {
                val last = forexSession.selectedSymbols.removeLast()
                last.selected = false
            }
            forexSession.selectedSymbols.add(item)
        } else
            forexSession.selectedSymbols.remove(item)

        if (forexSession.selectionLimitReached())
            getMvpView().showRateHistory()
    }
}