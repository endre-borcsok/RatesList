package com.example.app.ui.rateslist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.ItemViewCurrencyBinding

class RatesListAdapter(private val data: MutableList<RatesListElement>,
                       private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {

    companion object {
        const val BACKGROUND_DEFAULT = Color.TRANSPARENT
        const val BACKGROUND_SELECTED = Color.RED
    }

    interface OnItemClickListener {
        fun onAdapterItemClick(item: RatesListElement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemViewCurrencyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = data[position]
        holder.currencyNameLabel.text = element.symbol
        holder.currencyRateLabel.text = element.amount
        holder.currencyItemView.setBackgroundColor(if (element.selected)
            BACKGROUND_SELECTED else BACKGROUND_DEFAULT)
        holder.setOnItemClickListener(element, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(binding: ItemViewCurrencyBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val currencyNameLabel: TextView = itemView.findViewById(R.id.currency_name_label)
        val currencyRateLabel: TextView = itemView.findViewById(R.id.currency_rate_label)
        val currencyItemView: View = itemView.findViewById(R.id.currency_item_view)

        fun setOnItemClickListener(item: RatesListElement,
                                   onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                onItemClickListener.onAdapterItemClick(item)
            }
        }
    }
}