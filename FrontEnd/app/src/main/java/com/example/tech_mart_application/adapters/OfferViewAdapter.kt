package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.LayoutOfferItemBinding
import com.example.tech_mart_application.models.Offer

class OfferViewAdapter(private val listOffer: MutableList<Offer>) :
    RecyclerView.Adapter<OfferViewAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(val layoutOfferItemBinding: LayoutOfferItemBinding) :
        RecyclerView.ViewHolder(layoutOfferItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view =
            LayoutOfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listOffer.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = listOffer[position]
        holder.layoutOfferItemBinding.apply {
            offerDetail = offer
        }
    }
}