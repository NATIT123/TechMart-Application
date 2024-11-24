package com.example.tech_mart_application.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tech_mart_application.databinding.LayoutImageItemBinding
import com.example.tech_mart_application.databinding.SliderItemContainerBinding
import com.example.tech_mart_application.models.Banner

class ImageViewAdapter(
    private var listImage: MutableList<Bitmap>,
    private val mClickItem: OnClickItemListener
) :
    RecyclerView.Adapter<ImageViewAdapter.ImageViewHolder>() {

    interface OnClickItemListener {
        fun onRemoveItem(position: Int)
    }

    inner class ImageViewHolder(val layoutImageItemBinding: LayoutImageItemBinding) :
        RecyclerView.ViewHolder(layoutImageItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = listImage[position]
        holder.layoutImageItemBinding.apply {
            image.setImageBitmap(imageItem);
            removeImage.setOnClickListener {
                mClickItem.onRemoveItem(position);
            }
        }
    }
}