package com.example.tech_mart_application.activities.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.adapters.PendingOrderViewAdapter
import com.example.tech_mart_application.databinding.ActivityPendingOrderBinding
import com.example.tech_mart_application.models.Items

class PendingOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendingOrderBinding
    private lateinit var mPendingOrderViewAdapter: PendingOrderViewAdapter
    private val listItem = mutableListOf<Items>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPendingOrder()
        loadRecyclerView()

        //Btn Back
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadPendingOrder() {
        listItem.add(Items())
        listItem.add(Items())
        listItem.add(Items())
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listItem.isEmpty()
        if (listItem.isNotEmpty()) {
            mPendingOrderViewAdapter = PendingOrderViewAdapter(listItem)
            binding.rcvPending.apply {
                adapter = mPendingOrderViewAdapter
                layoutManager = LinearLayoutManager(
                    this@PendingOrderActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
        binding.isLoading = false
    }
}