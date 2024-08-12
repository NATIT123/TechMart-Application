package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutOfficialCategoryBinding
import com.example.tech_mart_application.databinding.LayoutRecommendContainerBinding
import com.example.tech_mart_application.models.Product

class RecommendationViewAdapter(private val listRecommendationProduct: MutableList<Product>) :
    RecyclerView.Adapter<RecommendationViewAdapter.RecommendationViewHolder>() {
    inner class RecommendationViewHolder(val layoutRecommendContainerBinding: LayoutRecommendContainerBinding) :
        RecyclerView.ViewHolder(layoutRecommendContainerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val view = LayoutRecommendContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRecommendationProduct.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendProduct = listRecommendationProduct[position]
        holder.layoutRecommendContainerBinding.product = recommendProduct
    }


}