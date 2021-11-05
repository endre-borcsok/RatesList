package com.example.app.presenters

import com.example.app.common.ForexSession
import com.example.app.common.Utils
import com.example.app.network.Api
import com.example.app.network.NetworkManager
import com.example.app.network.debug.DebugApiManager
import com.example.app.network.fixer.model.FixerRatesResponse
import com.example.app.ui.rateslist.RatesListContract
import com.example.app.ui.rateslist.RatesListElement
import com.example.app.ui.rateslist.RatesListPresenter
import com.google.gson.Gson
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*


class RatesListPresenterTest {

    private lateinit var ratesListPresenter: RatesListPresenter
    private val forexSession = ForexSession()
    private val ratesResponse = Gson().fromJson(DebugApiManager.json, FixerRatesResponse::class.java)

    @Before
    fun initTest() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        Utils.initForexSession(forexSession)

        val api: Api = mock(Api::class.java)
        val networkManager = NetworkManager(api)

        `when`(api.getHistoricalRates(anyString(), anyString(), anyString()))
            .thenReturn(Observable.just(ratesResponse))

        ratesListPresenter = RatesListPresenter(networkManager,
            forexSession, mock(RatesListContract.View::class.java))
    }

    @Test
    fun testItemClick() {
        val item = RatesListElement()
        ratesListPresenter.selectRatesListItem(item)
        assertTrue(item.selected)
        ratesListPresenter.selectRatesListItem(item)
        assertTrue(!item.selected)
    }

    @Test
    fun ratesAreCorrect() {
        val currencyAmount = 10.0f
        forexSession.currencyAmount = currencyAmount
        loadTodaysList()
        val todaysDate = Utils.getTodaysDate()
        val list = ratesListPresenter.getLatestRatesList()
        for (element in list) {
            val dailyRate = forexSession.dailyRates[todaysDate]?.get(element.symbol)
            val exchangedAmount = Utils.calculateRate(currencyAmount, dailyRate!!)
            assertTrue(element.amount == exchangedAmount)
        }
    }

    @Test
    fun testListLoads() {
        loadTodaysList()
        val list = ratesListPresenter.getLatestRatesList()
        assertTrue(list.size > 0)
    }

    @Test
    fun testRatesLoad() {
        loadTodaysList()
        val todaysDate = Utils.getTodaysDate()
        forexSession.dailyRates[todaysDate]?.let { assertTrue(it.isNotEmpty()) }
    }

    fun loadTodaysList() {
        val todaysDate = Utils.getTodaysDate()
        ratesResponse.date = todaysDate
        ratesListPresenter.loadRateHistory(todaysDate)
    }
}