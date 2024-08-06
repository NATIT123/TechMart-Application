package com.example.tech_mart_application.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityChangePasswordBinding
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_FORGOT_PASSWORD
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.OPTION_CHANGE_PASSWORD
import com.example.tech_mart_application.utils.Constants.Companion.OPTION_FORGOT_PASSWORD
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64


class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private var showPassword: Boolean = false
    private var showPasswordConfirm: Boolean = false
    private var showNewPassword: Boolean = false
    private lateinit var auth: FirebaseAuth
    private var option: Int = 0

    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        //Load Option
        loadOption()




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

    private fun loadOption() {
        if (intent.getIntExtra(KEY_FORGOT_PASSWORD, 0) == OPTION_FORGOT_PASSWORD) {
            binding.edtOldPassword.visibility = View.GONE
            option = OPTION_FORGOT_PASSWORD
        } else {
            option = OPTION_CHANGE_PASSWORD
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
        } else if (!Bcrypt.verify(binding.edtOldPassword.text.toString(), getPasswordHash())
        ) {
            showToast("Old Password is not correct")
            return
        } else if (binding.edtNewPassword.text.toString().isEmpty()) {
            showToast("Please enter new password")
            return
        } else if (binding.edtConfirmNewPassword.text.toString().isEmpty()) {
            showToast("Please enter confirm new password")
            return
        } else if (binding.edtNewPassword.text.toString() != binding.edtConfirmNewPassword.text.toString()
        ) {
            showToast("New password and confirm password must be the same")
            return
        } else {
            isLoading(true)
            changePassword()
        }
    }


    private fun navigateSignIn() {
        showToast("Please Sign in again.")
        val intent =
            Intent(
                this@ChangePasswordActivity,
                SignInActivity::class.java
            )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


    private fun changeOptionForgotPassword() {

    }

    private fun changeOptionChangePassword() {
        val oldPassword = binding.edtOldPassword.text.toString()
        val newPassword = binding.edtNewPassword.text.toString()
        val email = preferenceManager.getString(KEY_USER_EMAIL)

        val user = Firebase.auth.currentUser!!
        val credential = EmailAuthProvider
            .getCredential(email!!, oldPassword)
        //Sign In User
        auth.signInWithEmailAndPassword(email, oldPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //If Success authenticate account change password
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            showToast("User re-authenticated.")
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val passwordHashed =
                                            Bcrypt.hash(newPassword, Constants.SALT_ROUNDS)
                                        runBlocking {
                                            val file = File(filesDir, "my_file.bin")
                                            withContext(Dispatchers.IO) {
                                                FileOutputStream(file).use { output ->
                                                    output.write(passwordHashed)
                                                }
                                            }
                                        }
                                        navigateSignIn()
                                    } else {
                                        Log.d("MyApp", task.result.toString())
                                        showToast(task.result.toString())
                                    }
                                }
                        }
                        .addOnFailureListener {
                            isLoading(false)
                            Log.d("MyApp", it.message.toString())
                            showToast(it.message.toString())
                        }
                } else {
                    isLoading(false)
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        task.result.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }

    private fun changePassword() {
        if (option == OPTION_FORGOT_PASSWORD) {
            changeOptionForgotPassword()
        } else if (option == OPTION_CHANGE_PASSWORD) {
            changeOptionChangePassword()
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ChangePasswordActivity, message, Toast.LENGTH_SHORT).show()
    }


    private fun getPasswordHash(): ByteArray {
        val file = File(filesDir, "my_file.bin")
        lateinit var byteArray: ByteArray
        try {
            if (file.exists()) {
                val inputStream = FileInputStream(file)
                byteArray = inputStream.readBytes()
                inputStream.close()
            } else {
                println("File không tồn tại")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return byteArray

    }

}