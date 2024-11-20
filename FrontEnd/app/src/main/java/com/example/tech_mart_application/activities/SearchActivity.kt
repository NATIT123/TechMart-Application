package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchNow.queryHint = "Search Now"


        binding.searchNow.setOnClickListener {
            binding.searchNow.queryHint = "";
        }

        binding.searchNow.isActivated = true

        //Back
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}