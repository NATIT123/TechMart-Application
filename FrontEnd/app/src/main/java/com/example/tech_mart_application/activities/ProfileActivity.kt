package com.example.tech_mart_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityProfileBinding
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_FORGOT_PASSWORD
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ADDRESS
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.PreferenceManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        loadData()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity,EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
           finish()
        }

        //Button ChangePassword
        binding.btnChangePassword.setOnClickListener {
            val intent =Intent(this@ProfileActivity,ChangePasswordActivity::class.java)
            intent.putExtra(KEY_FORGOT_PASSWORD, Constants.OPTION_CHANGE_PASSWORD)
            startActivity(intent)
        }


    }

    private fun loadData(){
        val fullName = preferenceManager.getString(KEY_USER_FULL_NAME)!!
        val email = preferenceManager.getString(KEY_USER_EMAIL)!!
        val address = preferenceManager.getString(KEY_USER_ADDRESS)?:"123"
        val phone = preferenceManager.getString(PHONE_NUMBER)?:"32232323"
        val image = preferenceManager.getString(KEY_USER_IMAGE)!!
        val userDetail = User(fullName=fullName,email=email,address=address,phone=phone, role = "User")
        binding.user= userDetail
    }
}