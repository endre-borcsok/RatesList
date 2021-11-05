package com.example.app.ui.rateslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.common.Utils
import com.example.app.databinding.FragmentRatesListBinding
import com.example.app.ui.MainActivityController
import com.example.app.ui.base.BaseFragment
import com.example.app.ui.ratehistory.RateHistoryFragment


class RatesListFragment : BaseFragment<FragmentRatesListBinding,
        MainActivityController,
        RatesListContract.Presenter>(),
    RatesListContract.View, RatesListAdapter.OnItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ratesListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = RatesListAdapter(getPresenter().getLatestRatesList(),
                this@RatesListFragment)
        }

        binding.amountInputField.setOnEditorActionListener(getInputTextEditorActionListener())
        binding.amountInputField.setText(String.format("%s", forexSession.currencyAmount.toInt()))
        binding.selectedCurrencyLabel.text = forexSession.baseCurrency
        getPresenter().loadRateHistory(Utils.getTodaysDate())
    }

    override fun showRateHistory() {
        getActivityController().showFragment(RateHistoryFragment())
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun refreshList() {
        activity?.runOnUiThread {
            binding.ratesListRecycler.adapter?.notifyDataSetChanged()
        }
    }

    override fun refreshListItem(item: RatesListElement) {
        activity?.runOnUiThread {
            binding.ratesListRecycler.adapter?.notifyItemChanged(getPresenter()
                .getLatestRatesList()
                .indexOf(item))
        }
    }

    override fun onError() {
        showMessage(getString(R.string.something_went_wrong))
    }

    private fun getInputTextEditorActionListener() : TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                forexSession.currencyAmount = try {
                    v.text.toString().toFloat()
                } catch (exception: NumberFormatException) {
                    onError()
                    1.0f
                }
                getPresenter().refreshLatestRatesList()
                hideKeyboard(v)
                true
            } else {
                false
            }
        }
    }

    override fun onAdapterItemClick(item: RatesListElement) {
        getPresenter().selectRatesListItem(item)
        refreshListItem(item)
    }

    override fun getTitle(): Int {
        return R.string.title_exchange_rates
    }
}