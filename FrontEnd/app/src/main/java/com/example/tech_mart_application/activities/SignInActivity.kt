package com.example.tech_mart_application.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.Retrofit.ApiService
import com.example.tech_mart_application.databinding.ActivitySignInBinding
import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.SALT_ROUNDS
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var showPassword: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()


        auth = FirebaseAuth.getInstance()


        //Toggle IconPassword
        toggleIconPassword()

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


        //Handle Sign In
        binding.btnSignIn.setOnClickListener {
            isValidSignIn()
        }

        //Forgot Password
        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


    }

    private fun toggleIconPassword() {
        binding.btnTogglePassword.setOnClickListener {
            if (showPassword) {
                showPassword = false
                binding.edtPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnTogglePassword.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                showPassword = true
                R.drawable.baseline_visibility_off_24
                binding.edtPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnTogglePassword.setImageResource(R.drawable.baseline_visibility_24)
            }

            binding.edtPassword.setSelection(binding.edtPassword.text.length)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SignInActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidSignIn() {
        if (binding.edtEmail.text.toString().isEmpty()) {
            showToast("Please enter Email")
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()) {
            showToast("Email not valid")
            return
        } else if (binding.edtPassword.text.toString().isEmpty()) {
            showToast("Please enter Password")
            return
        } else {
            isLoading(true)
            signIn()
        }
    }


    private fun signIn() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val user = User(email = email, password = password, image = "", phone = "", fullName = "")
        handleLoginUser(user)

    }

    private fun handleLoginUser(user: User) {
        ApiService.apiService.loginUser(user).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        when (body.status) {
                            "ok" -> {
                                isLoading(false)

                                val passwordHashed = Bcrypt.hash(user.password, SALT_ROUNDS)

                                runBlocking {
                                    val file = File(filesDir, "my_file.bin")
                                    withContext(Dispatchers.IO) {
                                        FileOutputStream(file).use { output ->
                                            output.write(passwordHashed)
                                        }
                                    }
                                }



                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                                preferenceManager.putString(
                                    KEY_USER_ID, body.data.split(':')[1]
                                )
                                preferenceManager.putString(
                                    KEY_USER_EMAIL,
                                    binding.edtEmail.text.toString()
                                )
                                preferenceManager.putString(
                                    KEY_USER_FULL_NAME,
                                    body.data.split(':')[2]
                                )
                                preferenceManager.putString(KEY_USER_IMAGE, body.data.split(':')[3])
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                showToast("Login Successfully")
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
            }

            override fun onFailure(call: Call<DataResponse>, error: Throwable) {
                isLoading(false)
                showToast(error.message.toString())
            }

        })
    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun handleDataError(dataResponse: DataResponse) {
        isLoading(false)
        showToast(dataResponse.data)
    }
}