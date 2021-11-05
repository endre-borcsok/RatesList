package com.example.app.presenters

import android.content.Context
import com.example.app.common.ForexSession
import com.example.app.common.Symbols
import com.example.app.common.Utils
import com.example.app.ui.ratehistory.RateHistoryContract
import com.example.app.ui.ratehistory.RateHistoryElement
import com.example.app.ui.ratehistory.RateHistoryPresenter
import com.example.app.ui.rateslist.RatesListElement
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito

class RateHistoryPresenterTest {
    private lateinit var rateHistoryPresenter: RateHistoryPresenter
    private val dateString = "date"
    private val amount = 12.0f
    private val forexSession = ForexSession()
    private val selectedRate1 = RatesListElement()
    private val selectedRate2 = RatesListElement()

    @Before
    fun initTest() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        val viewMock = Mockito.mock(RateHistoryContract.View::class.java)
        val contextMock = Mockito.mock(Context::class.java)

        Mockito.`when`(viewMock.getAppContext())
            .thenReturn(contextMock)
        Mockito.`when`(viewMock.getAppContext().getString(anyInt()))
            .thenReturn(dateString)

        rateHistoryPresenter = RateHistoryPresenter(forexSession, viewMock)

        selectedRate1.symbol = Symbols.GBP.symbol
        selectedRate2.symbol = Symbols.CAD.symbol
        forexSession.currencyAmount = amount
        var rate = 1.0f

        for (key in forexSession.dailyRates.keys) {
            val rates = HashMap<String, Float>()
            rates[selectedRate1.symbol] = rate
            rates[selectedRate2.symbol] = rate
            forexSession.dailyRates[key] = rates
            rate += 0.5f
        }
    }

    @Test
    fun testRatesAreCorrect() {
        val list = getList()
        for (element in list) {
            if (element.date == dateString) continue
            val rate1 = forexSession.dailyRates[element.date]?.get(selectedRate1.symbol)
            val rate2 = forexSession.dailyRates[element.date]?.get(selectedRate2.symbol)
            val exchangedRate1 = Utils.calculateRate(amount, rate1!!)
            val exchangedRate2 = Utils.calculateRate(amount, rate2!!)
            assertTrue(exchangedRate1 == element.firstAmount)
            assertTrue(exchangedRate2 == element.secondAmount)
        }
    }

    @Test
    fun testListLoads() {
        assertTrue(getList().size > 0)
    }

    fun getList(): MutableList<RateHistoryElement> {
        forexSession.selectedSymbols.add(selectedRate1)
        forexSession.selectedSymbols.add(selectedRate2)
        return rateHistoryPresenter.getDailyRatesList()
    }
}