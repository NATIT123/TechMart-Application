package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutPendingOrderViewBinding
import com.example.tech_mart_application.models.Items
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product

class PendingOrderViewAdapter(
    private
    val listItem: MutableList<Items>
) :
    RecyclerView.Adapter<PendingOrderViewAdapter.PendingOrderViewHolder>() {

    inner class PendingOrderViewHolder(val layoutPendingOrderViewBinding: LayoutPendingOrderViewBinding) :
        RecyclerView.ViewHolder(layoutPendingOrderViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val view = LayoutPendingOrderViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PendingOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        val items = listItem[position]
        holder.layoutPendingOrderViewBinding.apply {
            itemsDetail = items
        }
    }


}