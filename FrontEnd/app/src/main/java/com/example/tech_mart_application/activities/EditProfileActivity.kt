package com.example.tech_mart_application.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.Retrofit.ApiService
import com.example.tech_mart_application.databinding.ActivityEditProfileBinding
import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ADDRESS
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var encodeImage: String

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val imageUri = result.data!!.data
                    try {
                        val inputStream = imageUri?.let { contentResolver.openInputStream(it) }
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.img.setImageBitmap(bitmap)
                        encodeImage = encodeImage(bitmap)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()
        loadData()

        encodeImage = preferenceManager.getString(
            KEY_USER_IMAGE
        )!!

        binding.btnBack.setOnClickListener {
            finish()
        }

        //Update profile
        binding.btnSave.setOnClickListener {
            binding.isLoading = true
            val id = preferenceManager.getString(KEY_USER_ID)
            val fullName = binding.edtFullName.text.toString()
            val address = binding.edtAddress.text.toString()
            val image = encodeImage
            val email = preferenceManager.getString(KEY_USER_EMAIL)
            val phone = binding.edtPhone.text.toString()
            updateUser(
                id!!,
                User(fullName = fullName, address = address, image = image, phone = phone,email=email!!)
            )
        }


        //Select Image
        binding.img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImage.launch(intent)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this@EditProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleDataError(dataResponse: DataResponse) {
        isLoading(false)
        showToast(dataResponse.data)
    }

    private fun updateUser(id: String, user: User) {
        ApiService.apiService.updateUser(
            id,
            user
        ).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val body = response.body()
                if (body != null) {
                    when (body.status) {
                        "ok" -> {
                            isLoading(false)

                            preferenceManager.putString(
                                Constants.KEY_USER_FULL_NAME, user.fullName
                            )
                            preferenceManager.putString(KEY_USER_ADDRESS, user.address)
                            preferenceManager.putString(PHONE_NUMBER, user.phone)
                            preferenceManager.putString(KEY_USER_IMAGE, user.image)
                            showToast("Change Profile Successfully")
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

    private fun getUserImage(url: String): Bitmap {
        val bytes = Base64.decode(url, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun loadData() {
        val fullName = preferenceManager.getString(Constants.KEY_USER_FULL_NAME)!!
        val email = preferenceManager.getString(KEY_USER_EMAIL)!!
        val address = preferenceManager.getString(KEY_USER_ADDRESS) ?: "Empty"
        val phone = preferenceManager.getString(PHONE_NUMBER) ?: "Empty"
        val image = preferenceManager.getString(KEY_USER_IMAGE)!!
        val userDetail = User(
            fullName = fullName,
            email = email,
            address = address,
            phone = phone,
        )
        binding.img.setImageBitmap(getUserImage(image))
        binding.user = userDetail
    }
}