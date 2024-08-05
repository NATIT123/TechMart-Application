package com.example.tech_mart_application.activities

import android.app.appsearch.BatchResultCallback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivitySignInBinding
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.SALT_ROUNDS
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.toxicbakery.bcrypt.Bcrypt
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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

    @Throws(NoSuchAlgorithmException::class)
    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.reset()
        md.update(password.toByteArray())
        val mdArray = md.digest()
        val sb = StringBuilder(mdArray.size * 2)
        for (b in mdArray) {
            val v = b.toInt() and 0xff
            if (v < 16) sb.append('0')
            sb.append(Integer.toHexString(v))
        }
        return sb.toString()
    }

    private fun signIn() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    isLoading(false)

                    val passwordHashed = hashPassword(password)

                    preferenceManager.putString(
                        Constants.KEY_USER_PASSWORD, passwordHashed
                    )

                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManager.putString(
                        Constants.KEY_USER_ID,
                        "1"
                    )
                    preferenceManager.putString(KEY_USER_EMAIL, binding.edtEmail.text.toString())
                    preferenceManager.putString(
                        KEY_USER_FULL_NAME,
                        "name1"
                    )
                    preferenceManager.putString(KEY_USER_IMAGE, "image1")
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    showToast("Login Successfully")
                } else {
                    // If sign in fails, display a message to the user.
                    isLoading(false)
                    showToast("Email or Password is not correct")
                }
            }

    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }
}