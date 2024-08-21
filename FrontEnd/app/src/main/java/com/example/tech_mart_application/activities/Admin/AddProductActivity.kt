package com.example.tech_mart_application.activities.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button Back
        binding.btnBack.setOnClickListener { finish() }


    }
}