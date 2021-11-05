package com.example.app.ui.ratehistory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.databinding.RateHistoryListBinding
import com.example.app.ui.MainActivityController
import com.example.app.ui.base.BaseFragment

class RateHistoryFragment : BaseFragment<RateHistoryListBinding,
        MainActivityController,
        RateHistoryContract.Presenter>(),
    RateHistoryContract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rateHistoryRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = RateHistoryAdapter(getPresenter().getDailyRatesList())
        }

        binding.currencyAmount.text = String.format("%.2f %s", forexSession.currencyAmount,
            forexSession.baseCurrency)
    }

    override fun getTitle(): Int {
        return R.string.title_rate_history
    }
}