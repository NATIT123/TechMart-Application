package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.HistoryPurchaseViewAdapter
import com.example.tech_mart_application.databinding.ActivityHistoryProductBinding
import com.example.tech_mart_application.models.Items
import com.example.tech_mart_application.models.Order
import com.example.tech_mart_application.models.Product

class HistoryProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryProductBinding
    private var listItems = mutableListOf<Items>()
    private lateinit var mHistoryPurchaseViewAdapter: HistoryPurchaseViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        loadRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadData() {
        listItems.add(Items(Product(quantity = 10), quantity = 20, order = Order()))
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listItems.isEmpty()
        if (listItems.isNotEmpty()) {
            mHistoryPurchaseViewAdapter = HistoryPurchaseViewAdapter(listItems)
            binding.rcvRecent.apply {
                adapter = mHistoryPurchaseViewAdapter
                layoutManager = LinearLayoutManager(this@HistoryProductActivity)
            }
        }
        binding.isLoading = false
    }
}