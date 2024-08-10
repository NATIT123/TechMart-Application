package com.example.tech_mart_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tech_mart_application.database.ProductDatabase
import com.example.tech_mart_application.databinding.ActivityMainBinding
import com.example.tech_mart_application.viewModel.ProductViewModel
import com.example.tech_mart_application.viewModel.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val viewModel: ProductViewModel by lazy {
        val productDatabase = ProductDatabase.getInstance(this)
        val productViewModelFactory = ProductViewModelFactory(productDatabase)
        ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_frame) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.myBottomNav, navController)
    }
}