package com.example.tech_mart_application.activities

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityChangePasswordBinding
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_PASSWORD
import com.example.tech_mart_application.utils.PreferenceManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64


class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var preferenceManager: PreferenceManager
    private var showPassword: Boolean = false
    private var showPasswordConfirm: Boolean = false
    private var showNewPassword: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()



        toggleIconPassword(binding.btnToggleOldPassword, binding.edtOldPassword)
        toggleIconPasswordConfirm(
            binding.btnToggleConfirmNewPassword,
            binding.edtConfirmNewPassword
        )
        toggleIconNewPassword(binding.btnToggleNewPassword, binding.edtNewPassword)

        //Handle Change Password
        binding.btnChangePassword.setOnClickListener {
            isValidChangePassword()
        }


    }

    private fun toggleIconPassword(buttonIcon: ImageButton, buttonPassword: EditText) {
        buttonIcon.setOnClickListener {
            if (showPassword) {
                showPassword = false
                buttonPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                showPassword = true
                buttonPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_24)
            }

            buttonPassword.setSelection(buttonPassword.text.length)
        }
    }

    private fun toggleIconPasswordConfirm(buttonIcon: ImageButton, buttonPassword: EditText) {
        buttonIcon.setOnClickListener {
            if (showPasswordConfirm) {
                showPasswordConfirm = false
                buttonPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                showPasswordConfirm = true
                buttonPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_24)
            }

            buttonPassword.setSelection(buttonPassword.text.length)
        }
    }

    private fun toggleIconNewPassword(buttonIcon: ImageButton, buttonPassword: EditText) {
        buttonIcon.setOnClickListener {
            if (showNewPassword) {
                showNewPassword = false
                buttonPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                showNewPassword = true
                buttonPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                buttonIcon.setImageResource(R.drawable.baseline_visibility_24)
            }

            buttonPassword.setSelection(buttonPassword.text.length)
        }
    }

    private fun isValidChangePassword() {
        if (binding.edtOldPassword.text.toString().isEmpty()) {
            showToast("Please enter your old password")
            return
        } else if (getSalt()
        ) {
            showToast("Old Password is not correct")
            return
        } else if (binding.edtNewPassword.text.toString().isEmpty()) {
            showToast(binding.edtOldPassword.text.toString())
//            showToast("Please enter new password")
            return
        } else if (binding.edtConfirmNewPassword.text.toString().isEmpty()) {
            showToast("Please enter confirm new password")
            return
        } else if (binding.edtNewPassword.text.toString() != binding.edtConfirmNewPassword.toString()
        ) {
            showToast("New password and confirm password must be the same")
            return
        } else {
            isLoading(true)
            changePassword()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(NoSuchAlgorithmException::class)
    fun getSalt(): String? {
        val sr = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(32)
        sr.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt)
    }

    private fun changePassword() {

    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
    }
}