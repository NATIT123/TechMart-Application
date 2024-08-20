package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutDeliveryItemBinding
import com.example.tech_mart_application.models.Order

class OutForDeliveryViewAdapter(private val listOrder: MutableList<Order>) :
    RecyclerView.Adapter<OutForDeliveryViewAdapter.OutForDeliveryViewHolder>() {

    inner class OutForDeliveryViewHolder(val layoutOutForDeliveryBinding: LayoutDeliveryItemBinding) :
        RecyclerView.ViewHolder(layoutOutForDeliveryBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutForDeliveryViewHolder {
        val view =
            LayoutDeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutForDeliveryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: OutForDeliveryViewHolder, position: Int) {
        val order = listOrder[position]
        holder.layoutOutForDeliveryBinding.apply {
            orderDetailItem = order
        }
    }
}