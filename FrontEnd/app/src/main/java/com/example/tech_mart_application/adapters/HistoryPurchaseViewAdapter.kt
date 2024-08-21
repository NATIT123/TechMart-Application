package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutHistoryItemBinding
import com.example.tech_mart_application.models.Items

class HistoryPurchaseViewAdapter(private val listItems: MutableList<Items>) :
    RecyclerView.Adapter<HistoryPurchaseViewAdapter.HistoryPurchaseViewHolder>() {

    inner class HistoryPurchaseViewHolder(val layoutHistoryItemBinding: LayoutHistoryItemBinding) :
        RecyclerView.ViewHolder(layoutHistoryItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPurchaseViewHolder {
        val view =
            LayoutHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryPurchaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: HistoryPurchaseViewHolder, position: Int) {
        val item = listItems[position]
        holder.layoutHistoryItemBinding.itemsDetail = item
    }
}