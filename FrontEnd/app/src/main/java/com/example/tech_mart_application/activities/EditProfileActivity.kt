package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityEditProfileBinding
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.PreferenceManager

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditProfileBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()
//        loadData()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadData(){
        val fullName = preferenceManager.getString(Constants.KEY_USER_FULL_NAME)!!
        val email = preferenceManager.getString(Constants.KEY_USER_EMAIL)!!
        val address = preferenceManager.getString(Constants.KEY_USER_ADDRESS)?:"123"
        val phone = preferenceManager.getString(Constants.PHONE_NUMBER)?:"32232323"
        val image = preferenceManager.getString(Constants.KEY_USER_IMAGE)!!
        val userDetail = User(fullName=fullName,email=email,address=address,phone=phone, role = "User")
        binding.user= userDetail
    }
}