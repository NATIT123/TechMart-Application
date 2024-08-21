package com.example.tech_mart_application.activities.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=  ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}