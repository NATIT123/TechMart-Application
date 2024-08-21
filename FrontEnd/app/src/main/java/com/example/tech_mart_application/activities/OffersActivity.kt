package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.NotificationViewAdapter
import com.example.tech_mart_application.adapters.OfferViewAdapter
import com.example.tech_mart_application.databinding.ActivityOffersBinding
import com.example.tech_mart_application.models.Offer

class OffersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOffersBinding
    private lateinit var mOfferViewAdapter: OfferViewAdapter
    private val listOffer = mutableListOf<Offer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadOffer()
        loadRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadOffer() {
        listOffer.add(
            Offer(
                offerCode = "OfferCode",
                description = "Description",
                minValue = "RS 100",
                validTo = "17/8/2024",
                discountValue = "RS 200"
            )
        )
        listOffer.add(
            Offer(
                offerCode = "OfferCode",
                description = "Description",
                minValue = "RS 100",
                validTo = "17/8/2024",
                discountValue = "RS 200"
            )
        )
        listOffer.add(
            Offer(
                offerCode = "OfferCode",
                description = "Description",
                minValue = "RS 100",
                validTo = "17/8/2024",
                discountValue = "RS 200"
            )
        )
        listOffer.add(
            Offer(
                offerCode = "OfferCode",
                description = "Description",
                minValue = "RS 100",
                validTo = "17/8/2024",
                discountValue = "RS 200"
            )
        )
        listOffer.add(
            Offer(
                offerCode = "OfferCode",
                description = "Description",
                minValue = "RS 100",
                validTo = "17/8/2024",
                discountValue = "RS 200"
            )
        )
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.rcvOffer.apply {
            binding.isEmpty = listOffer.isEmpty()
            if (listOffer.isNotEmpty()) {
                mOfferViewAdapter = OfferViewAdapter(listOffer)
                binding.rcvOffer.apply {
                    adapter = mOfferViewAdapter
                    layoutManager = LinearLayoutManager(
                        this@OffersActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    addItemDecoration(
                        DividerItemDecoration(
                            this@OffersActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
        }
        binding.isLoading = false
    }
}