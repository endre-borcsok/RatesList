package com.example.app.di

import com.example.app.ui.ratehistory.RateHistoryFragment
import com.example.app.ui.ratehistory.RateHistoryModule
import com.example.app.ui.rateslist.RatesListFragment
import com.example.app.ui.rateslist.RatesListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [RatesListModule::class])
    internal abstract fun bindRatesListFragment(): RatesListFragment

    @ContributesAndroidInjector(modules = [RateHistoryModule::class])
    internal abstract fun bindRateHistoryFragment(): RateHistoryFragment
}