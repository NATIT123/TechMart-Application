package com.example.tech_mart_application.activities.admin

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityCreateUserBinding
import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.retrofit.ApiService
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ADDRESS
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.Constants.Companion.SALT_ROUNDS
import com.example.tech_mart_application.utils.PreferenceManager
import com.toxicbakery.bcrypt.Bcrypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class CreateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var preferenceManager: PreferenceManager

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        //Create new User
        binding.btnCreateNewUser.setOnClickListener {
            binding.isLoading = true
            val image =
                Uri.parse("android.resource://${packageName}/${R.drawable.avatar}")
            contentResolver.openInputStream(image)
            val id = preferenceManager.getString(KEY_USER_ID)
            val fullName = binding.edtFullName.text.toString()
            val address = binding.edtAddress.text.toString()
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhone.text.toString()
            addUser(
                id!!,
                User(
                    fullName = fullName,
                    address = address,
                    image = encodeImage(getImageDefault(image)),
                    phone = phone,
                    email = email,
                    password = "Password123"
                )
            )
        }
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun getImageDefault(imageUri: Uri): Bitmap {
        val inputStream = imageUri.let { contentResolver.openInputStream(it) }
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this@CreateUserActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleDataError(dataResponse: DataResponse) {
        isLoading(false)
        showToast(dataResponse.data)
    }

    private fun addUser(adminId: String, user: User) {
        ApiService.apiService.createUser(adminId, user).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val body = response.body()
                if (body != null) {
                    when (body.status) {
                        "ok" -> {
                            isLoading(false)
                            showToast("Create User Successfully")
                            finish()
                        }

                        "notok" -> {
                            handleDataError(body)
                        }

                        else -> {
                            handleDataError(body)
                        }
                    }
                } else {
                    isLoading(false)
                    showToast(response.message())
                }

            }

            override fun onFailure(call: Call<DataResponse>, error: Throwable) {
                isLoading(false)
                showToast(error.message.toString())
            }
        })
    }
}