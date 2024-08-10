package com.example.tech_mart_application.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.LayoutOfficialCategoryBinding
import com.example.tech_mart_application.models.Category

class CategoryViewAdapter(private val listCategory: MutableList<Category>,private val context:Context) :
    RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class CategoryViewHolder(val layoutOfficialCategoryBinding: LayoutOfficialCategoryBinding) :
        RecyclerView.ViewHolder(layoutOfficialCategoryBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutOfficialCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val category = listCategory[position]
        holder.layoutOfficialCategoryBinding.apply {
            Glide.with(holder.itemView).load(category.picUrl).into(imgCategory)
            root.setOnClickListener {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            tvCategory.text = category.title

            if (selectedPosition == position) {
                imgCategory.setBackgroundResource(0)
                mainLayout.setBackgroundResource(R.drawable.background_blue_corner_15)
                tvCategory.visibility  = View.VISIBLE
            }
            else{
                tvCategory.visibility  = View.GONE
            }
        }


    }

}