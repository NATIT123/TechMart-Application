package com.example.tech_mart_application.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutItemOrdersBinding
import com.example.tech_mart_application.models.Order

class OrderViewAdapter(private val listOrder: MutableList<Order>) :
    RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val layoutItemOrdersBinding: LayoutItemOrdersBinding) :
        RecyclerView.ViewHolder(layoutItemOrdersBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        var order = listOrder[position]
        var title = ""
        holder.layoutItemOrdersBinding.apply {
            orderDetail = order
        }
    }
}