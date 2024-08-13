package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutViewCartBinding
import com.example.tech_mart_application.models.Order

class CartViewAdapter(private val listOrder: Order) :
    RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {
    inner class CartViewHolder(val layoutViewCartBinding: LayoutViewCartBinding) :
        RecyclerView.ViewHolder(layoutViewCartBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutViewCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOrder.listProduct?.size!!
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val listProduct = listOrder.listProduct?.get(position)
        holder.layoutViewCartBinding.product = listProduct
    }

}