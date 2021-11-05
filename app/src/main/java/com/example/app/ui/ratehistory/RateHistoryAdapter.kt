package com.example.app.ui.ratehistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ItemViewCurrencyHistoryBinding

class RateHistoryAdapter(private val data: MutableList<RateHistoryElement>)
    : RecyclerView.Adapter<RateHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemViewCurrencyHistoryBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = data[position]
        holder.dateLabel.text = element.date
        holder.currencyAmount1.text = element.firstAmount
        holder.currencyAmount2.text = element.secondAmount
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(binding: ItemViewCurrencyHistoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val dateLabel = binding.dateLabel
        val currencyAmount1 = binding.currencyAmountLabel1
        val currencyAmount2 = binding.currencyAmountLabel2
        }
}