package com.example.tech_mart_application.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
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


        binding.btnEdit.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        //Button ChangePassword
        binding.btnChangePassword.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            intent.putExtra(KEY_FORGOT_PASSWORD, Constants.OPTION_CHANGE_PASSWORD)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun getUserImage(url: String): Bitmap {
        val bytes = Base64.decode(url, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


    private fun loadData() {
        val fullName = preferenceManager.getString(KEY_USER_FULL_NAME)!!
        val email = preferenceManager.getString(KEY_USER_EMAIL)!!
        val address = preferenceManager.getString(KEY_USER_ADDRESS) ?: "Empty"
        val phone = preferenceManager.getString(PHONE_NUMBER) ?: "Empty"
        val image = preferenceManager.getString(KEY_USER_IMAGE)!!
        binding.img.setImageBitmap(getUserImage(image))
        val userDetail = User(
            fullName = fullName,
            email = email,
            address = address,
            phone = phone,
            role = "User"
        )
        binding.user = userDetail
    }
}