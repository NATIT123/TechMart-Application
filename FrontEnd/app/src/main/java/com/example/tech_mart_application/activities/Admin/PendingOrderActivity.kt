package com.example.tech_mart_application.activities.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.PendingOrderViewAdapter
import com.example.tech_mart_application.databinding.ActivityPendingOrderBinding
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product

class PendingOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendingOrderBinding
    private lateinit var mPendingOrderViewAdapter: PendingOrderViewAdapter
    private val listProduct = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPendingOrder()
        loadRecyclerView()
    }

    private fun loadPendingOrder() {
        listProduct.add(Product())
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listProduct.isEmpty()
        if (listProduct.isEmpty()) {
            binding.rcvPending.apply {
//                mPendingOrderViewAdapter = PendingOrderViewAdapter()
                adapter = mPendingOrderViewAdapter
                layoutManager = LinearLayoutManager(
                    this@PendingOrderActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }
}