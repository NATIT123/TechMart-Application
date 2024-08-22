package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutWishlistItemBinding
import com.example.tech_mart_application.models.Product

class WishlistViewAdapter(private val listProduct: MutableList<Product>) :
    RecyclerView.Adapter<WishlistViewAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(val layoutWishlistItemBinding: LayoutWishlistItemBinding) :
        RecyclerView.ViewHolder(layoutWishlistItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutWishlistItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return WishlistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
       val product = listProduct[position]
        holder.layoutWishlistItemBinding.apply {
            productDetailItem = product
        }
    }
}