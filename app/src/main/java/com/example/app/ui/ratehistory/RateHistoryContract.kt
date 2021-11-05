package com.example.app.ui.ratehistory

import com.example.app.ui.base.BaseContract

interface RateHistoryContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getDailyRatesList(): MutableList<RateHistoryElement>
    }

    interface View : BaseContract.View
}