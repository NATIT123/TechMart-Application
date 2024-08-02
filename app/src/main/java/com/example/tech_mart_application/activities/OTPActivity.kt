package com.example.tech_mart_application.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.tech_mart_application.databinding.ActivityOtpactivityBinding
import com.example.tech_mart_application.utils.Constants.Companion.PHONE_NUMBER
import com.example.tech_mart_application.utils.Constants.Companion.VERIFICATION_ID
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding

    private var resendEnabled = false

    private val resendTime = 60

    private var selectedOTPPosition = 0

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mPhoneNumber: String
    private lateinit var mVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()


        getData()


        setPhoneNumber()


        binding.edtInputCode1.addTextChangedListener(textWatcher)
        binding.edtInputCode2.addTextChangedListener(textWatcher)
        binding.edtInputCode3.addTextChangedListener(textWatcher)
        binding.edtInputCode4.addTextChangedListener(textWatcher)
        binding.edtInputCode5.addTextChangedListener(textWatcher)
        binding.edtInputCode6.addTextChangedListener(textWatcher)

        showKeyBoard(binding.edtInputCode1)
        startCountDownTimer()

        binding.btnResendOTP.setOnClickListener {
            if (resendEnabled) {
                startCountDownTimer()
                sendOTPAgain("+84$mPhoneNumber")
            }
        }

        binding.btnVerifyOTP.setOnClickListener {
            val generateOTP =
                "${binding.edtInputCode1.text}${binding.edtInputCode2.text}${binding.edtInputCode3.text}${binding.edtInputCode4.text}${binding.edtInputCode5.text}${binding.edtInputCode6.text}"

            if (generateOTP.length == 6) {
                verifyOTP(generateOTP)
            } else {
                Toast.makeText(this@OTPActivity, "Please Enter your OTP", Toast.LENGTH_SHORT).show()
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setPhoneNumber() {
        if (!mPhoneNumber.isNullOrEmpty()) {
            binding.tvPhoneNumber.text = "-$mPhoneNumber"
        }
    }


    private fun startCountDownTimer() {
        resendEnabled = false
        binding.timeResendOtp.visibility = View.VISIBLE
        binding.btnResendOTP.setTextColor(Color.parseColor("#99000000"))

        object : CountDownTimer((resendTime * 1000).toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                binding.timeResendOtp.text = "(${p0 / 1000})"
            }

            override fun onFinish() {
                binding.btnResendOTP.setTextColor(Color.parseColor("#FF0000"))
                binding.timeResendOtp.visibility = View.GONE
                resendEnabled = true

            }

        }.start()
    }

    private fun showKeyBoard(otpET: EditText) {
        otpET.requestFocus()

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0 != null) {
                if (p0.isNotEmpty()) {
                    if (selectedOTPPosition == 0) {
                        selectedOTPPosition = 1
                        showKeyBoard(binding.edtInputCode2)
                    } else if (selectedOTPPosition == 1) {
                        selectedOTPPosition = 2
                        showKeyBoard(binding.edtInputCode3)
                    } else if (selectedOTPPosition == 2) {
                        selectedOTPPosition = 3
                        showKeyBoard(binding.edtInputCode4)
                    } else if (selectedOTPPosition == 3) {
                        selectedOTPPosition = 4
                        showKeyBoard(binding.edtInputCode5)
                    } else if (selectedOTPPosition == 4) {
                        selectedOTPPosition = 5
                        showKeyBoard(binding.edtInputCode6)
                    }
                }
            }
        }


    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (selectedOTPPosition) {
                5 -> {
                    selectedOTPPosition = 4
                    showKeyBoard(binding.edtInputCode5)
                }

                4 -> {
                    selectedOTPPosition = 3
                    showKeyBoard(binding.edtInputCode4)
                }

                3 -> {
                    selectedOTPPosition = 2
                    showKeyBoard(binding.edtInputCode3)
                }

                2 -> {
                    selectedOTPPosition = 1
                    showKeyBoard(binding.edtInputCode2)
                }

                1 -> {
                    selectedOTPPosition = 0
                    showKeyBoard(binding.edtInputCode1)
                }
            }
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun getData() {
        mPhoneNumber = intent.getStringExtra(PHONE_NUMBER).toString()
        mVerificationId = intent.getStringExtra(VERIFICATION_ID).toString()
    }

    private fun verifyOTP(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =
                        Intent(this@OTPActivity, ChangePasswordActivity::class.java)
                    intent.putExtra(PHONE_NUMBER, mPhoneNumber)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            this@OTPActivity,
                            (task.exception as FirebaseAuthInvalidCredentialsException).message.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
    }

    private fun sendOTPAgain(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    mVerificationId = verificationId
                    Toast.makeText(this@OTPActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(message: FirebaseException) {
                    Toast.makeText(this@OTPActivity, message.toString(), Toast.LENGTH_SHORT).show()
                }

            }) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
