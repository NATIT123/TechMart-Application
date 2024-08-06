package com.example.tech_mart_application.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.SliderLayoutBinding
import com.example.tech_mart_application.models.SliderInfo

class SliderLayout(private val listSliderInfo: MutableList<SliderInfo>) :
    RecyclerView.Adapter<SliderLayout.SliderViewHolder>() {

    inner class SliderViewHolder(val sliderLayoutBinding: SliderLayoutBinding) :
        RecyclerView.ViewHolder(sliderLayoutBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = SliderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSliderInfo.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val sliderInfo = listSliderInfo[position]
        holder.sliderLayoutBinding.apply {
            imageSlider.setImageResource(sliderInfo.image)
            headerTitle.setText(sliderInfo.header)
            subtitleTitle.setText(sliderInfo.subtitle)
        }
    }
}