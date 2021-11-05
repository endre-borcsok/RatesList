package com.example.app.ui.ratehistory

import com.example.app.common.ForexSessionContract
import com.example.app.databinding.RateHistoryListBinding
import dagger.Module
import dagger.Provides

@Module
class RateHistoryModule {

    @Provides
    internal fun providesBinding(view: RateHistoryFragment): RateHistoryListBinding {
        return RateHistoryListBinding.inflate(view.layoutInflater)
    }

    @Provides
    internal fun provideRateHistoryPresenter(forexSession: ForexSessionContract,
                                             view: RateHistoryFragment)
    : RateHistoryContract.Presenter {
        return RateHistoryPresenter(forexSession, view)
    }
}