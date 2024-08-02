package com.example.tech_mart_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tech_mart_application.databinding.ActivityForgotPasswordBinding
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.Constants.Companion.VERIFICATION_ID
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnGetOTP.setOnClickListener {
            getOTP()
        }
    }

    private fun getOTP() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnGetOTP.visibility = View.GONE
        val phoneNumber = binding.edtPhoneNumber.text.toString().trim()
        if (phoneNumber.isEmpty()) {
            Toast.makeText(
                this@ForgotPasswordActivity,
                "Please Enter Phone Number",
                Toast.LENGTH_SHORT
            ).show()
            binding.progressBar.visibility = View.GONE
            binding.btnGetOTP.visibility = View.VISIBLE
        } else {

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+84$phoneNumber") // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        binding.progressBar.visibility = View.GONE
                        binding.btnGetOTP.visibility = View.VISIBLE
                        signInWithPhoneAuthCredential(credential, phoneNumber)
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {
                        binding.progressBar.visibility = View.GONE
                        binding.btnGetOTP.visibility = View.VISIBLE
                        Toast.makeText(this@ForgotPasswordActivity, "OTP Sent", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@ForgotPasswordActivity, OTPActivity::class.java)
                        intent.putExtra(PHONE_NUMBER, phoneNumber)
                        intent.putExtra(VERIFICATION_ID, phoneNumber)
                        startActivity(intent)
                    }

                    override fun onVerificationFailed(message: FirebaseException) {
                        binding.progressBar.visibility = View.GONE
                        binding.btnGetOTP.visibility = View.VISIBLE
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
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phoneNumber: String
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =
                        Intent(this@ForgotPasswordActivity, ChangePasswordActivity::class.java)
                    intent.putExtra(PHONE_NUMBER, phoneNumber)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
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

}