package com.example.tech_mart_application.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tech_mart_application.database.ProductDatabase

class ProductViewModelFactory(private val productDatabase: ProductDatabase,private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(productDatabase,context) as T
    }
}