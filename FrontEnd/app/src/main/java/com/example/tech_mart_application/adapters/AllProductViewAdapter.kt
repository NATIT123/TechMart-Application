package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutProductItemBinding
import com.example.tech_mart_application.models.Product

class AllProductViewAdapter(private val listProduct: MutableList<Product>) :
    RecyclerView.Adapter<AllProductViewAdapter.AllProductViewHolder>() {
    inner class AllProductViewHolder(val layoutProductItemBinding: LayoutProductItemBinding) :
        RecyclerView.ViewHolder(layoutProductItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        val view =
            LayoutProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
        val product = listProduct[position]
        holder.layoutProductItemBinding.productDetail = product
    }
}