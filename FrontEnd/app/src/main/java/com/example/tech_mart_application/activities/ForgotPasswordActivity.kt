package com.example.tech_mart_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tech_mart_application.retrofit.ApiService
import com.example.tech_mart_application.databinding.ActivityForgotPasswordBinding
import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.Constants.Companion.VERIFICATION_ID
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        auth = FirebaseAuth.getInstance()

        binding.btnGetOTP.setOnClickListener {
            getOTP()
        }

        //Handle Back Button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun getOTP() {
        isLoading(true)
        val phoneNumber = binding.edtPhoneNumber.text.toString().trim()
        if (phoneNumber.isEmpty()) {
            Toast.makeText(
                this@ForgotPasswordActivity,
                "Please Enter Phone Number",
                Toast.LENGTH_SHORT
            ).show()
            isLoading(false)
        } else if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(
                this@ForgotPasswordActivity,
                "Phone number is not valid",
                Toast.LENGTH_SHORT
            ).show()
            isLoading(false)
        } else {
            handlePhoneExistApi(phoneNumber)
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phoneNumber: String,
        dataResponse: DataResponse
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =
                        Intent(this@ForgotPasswordActivity, ChangePasswordActivity::class.java)
                    intent.putExtra(PHONE_NUMBER, phoneNumber)
                    intent.putExtra(KEY_USER_ID, dataResponse.data.split(':')[1])
                    Log.d("MyApp", dataResponse.data.split(':')[1])
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.d("MyApp", dataResponse.data.split(':')[1])
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            (task.exception as FirebaseAuthInvalidCredentialsException).message.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberUtil = PhoneNumberUtil.createInstance(applicationContext)
        return try {
            phoneNumberUtil.parse(phoneNumber, "VN")
            true
        } catch (e: NumberParseException) {
            false
        }
    }


    private fun handlePhoneExistApi(phoneNumber: String) {
        ApiService.apiService.isPhoneExist(phoneNumber).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        when (body.status) {
                            "ok" -> {
                                isLoading(false)
                                val options = PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber("+84$phoneNumber") // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(this@ForgotPasswordActivity) // Activity (for callback binding)
                                    .setCallbacks(object :
                                        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                            isLoading(false)
                                            signInWithPhoneAuthCredential(
                                                credential,
                                                phoneNumber,
                                                body
                                            )
                                        }

                                        override fun onCodeSent(
                                            verificationId: String,
                                            forceResendingToken: PhoneAuthProvider.ForceResendingToken
                                        ) {
                                            isLoading(false)
                                            Toast.makeText(
                                                this@ForgotPasswordActivity,
                                                "OTP Sent",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                            val intent = Intent(
                                                this@ForgotPasswordActivity,
                                                OTPActivity::class.java
                                            )
                                            intent.putExtra(PHONE_NUMBER, phoneNumber)
                                            preferenceManager.putString(
                                                KEY_USER_ID,
                                                body.data.split(':')[1]
                                            )
                                            intent.putExtra(VERIFICATION_ID, verificationId)
                                            startActivity(intent)
                                        }

                                        override fun onVerificationFailed(message: FirebaseException) {
                                            isLoading(false)
                                            Toast.makeText(
                                                this@ForgotPasswordActivity,
                                                message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }) // OnVerificationStateChangedCallbacks
                                    .build()
                                PhoneAuthProvider.verifyPhoneNumber(options)
                            }

                            "notok" -> {
                                handleDataError(body)
                            }

                            else -> {
                                handleDataError(body)
                            }
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

    private fun handleDataError(dataResponse: DataResponse) {
        isLoading(false)
        showToast(dataResponse.data)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ForgotPasswordActivity, message, Toast.LENGTH_SHORT).show()
    }


}