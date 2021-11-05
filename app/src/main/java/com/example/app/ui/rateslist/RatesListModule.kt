package com.example.app.ui.rateslist

import com.example.app.common.ForexSessionContract
import com.example.app.databinding.FragmentRatesListBinding
import com.example.app.network.NetworkManagerContract
import dagger.Module
import dagger.Provides

@Module
class RatesListModule {

    @Provides
    internal fun provideBinding(view: RatesListFragment) : FragmentRatesListBinding {
        return FragmentRatesListBinding.inflate(view.layoutInflater)
    }

    @Provides
    internal fun provideRateListPresenter(networkManager: NetworkManagerContract,
                                          forexSession: ForexSessionContract,
                                          view: RatesListFragment)
    : RatesListContract.Presenter {
        return RatesListPresenter(networkManager, forexSession, view)
    }
}