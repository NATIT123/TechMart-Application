package com.example.tech_mart_application.activities.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.AllProductViewAdapter
import com.example.tech_mart_application.databinding.ActivityAllProductBinding
import com.example.tech_mart_application.models.Category
import com.example.tech_mart_application.models.Product
import kotlin.concurrent.timerTask

class AllProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductBinding
    private lateinit var mAllProductViewAdapter: AllProductViewAdapter
    private val listProduct = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadProduct()
        loadRecyclerView()
    }


    private fun loadProduct() {
        val category = Category(title = "Category")
        listProduct.add(Product(category = category))
    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.isEmpty = listProduct.isEmpty()
        if (listProduct.isNotEmpty()) {
            mAllProductViewAdapter = AllProductViewAdapter(listProduct)
            binding.rcvProduct.apply {
                adapter = mAllProductViewAdapter
                layoutManager = LinearLayoutManager(
                    this@AllProductActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                addItemDecoration(
                    DividerItemDecoration(
                        this@AllProductActivity,
                        DividerItemDecoration.VERTICAL,
                    )
                )
            }
        }
    }


}