package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutPendingOrderViewBinding
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product

class PendingOrderViewAdapter(
    private val listProduct: MutableList<Product>, private
    val listOrder: MutableList<Order>
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
        return listOrder.size
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        val order = listOrder[position]
        val product = order.listProduct?.get(position)
        holder.layoutPendingOrderViewBinding.apply {
            orderDetail = order
            productDetail = product
        }
    }


}