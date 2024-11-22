package com.example.tech_mart_application.activities.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.adapters.OutForDeliveryViewAdapter
import com.example.tech_mart_application.databinding.ActivityOutForDeliveryBinding
import com.example.tech_mart_application.models.Order

class OutForDeliveryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutForDeliveryBinding
    private lateinit var mOutForDeliveryViewAdapter: OutForDeliveryViewAdapter
    private val listOrder = mutableListOf<Order>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutForDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadOrder()
        loadRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadOrder() {
        listOrder.add(Order(orderStatus = "Received"))
        listOrder.add(Order(orderStatus = "Not Received"))
        listOrder.add(Order(orderStatus = "Pending"))
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listOrder.isEmpty()
        if (listOrder.isNotEmpty()) {
            mOutForDeliveryViewAdapter = OutForDeliveryViewAdapter(listOrder)
            binding.rcvDelivery.apply {
                adapter = mOutForDeliveryViewAdapter
                layoutManager = LinearLayoutManager(
                    this@OutForDeliveryActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
        binding.isLoading = false
    }
}