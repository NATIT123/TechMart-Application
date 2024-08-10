package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tech_mart_application.databinding.SliderItemContainerBinding
import com.example.tech_mart_application.models.Banner

class BannerViewAdapter(private var listBanner: List<Banner>) :
    RecyclerView.Adapter<BannerViewAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(val sliderItemContainerBinding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(sliderItemContainerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view =
            SliderItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBanner.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = listBanner[position]
        holder.sliderItemContainerBinding.apply {
            Glide.with(holder.itemView).load(banner.image).into(imageSlider)
        }
    }
}