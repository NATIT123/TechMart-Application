package com.example.tech_mart_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivitySignUpBinding
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_COLLECTION_USERS
import com.example.tech_mart_application.utils.Constants.Companion.KEY_IS_SIGNED_IN
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_PASSWORD
import com.example.tech_mart_application.utils.Constants.Companion.SALT_ROUNDS
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var auth: FirebaseAuth


    private var showPassword: Boolean = false
    private var showPasswordConfirm: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        auth = FirebaseAuth.getInstance()

        toggleIconPassword(binding.btnTogglePassword, binding.edtPassword)
        toggleIconPasswordConfirm(binding.btnToggleConfirmPassword, binding.edtConfirmPassword)


        //Screen SignIn
        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
        }


        //Handle Sign Up Account
        binding.btnSignUp.setOnClickListener {
            isValidSignUpDetails()
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


    private fun isValidSignUpDetails(): Boolean {
        if (binding.edtFullName.text.toString().trim().isEmpty()) {
            showToast("Please Enter fullName")
            return false
        } else if (binding.edtEmail.text.toString().trim().isEmpty()) {
            showToast("Please Enter email")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString())
                .matches()
        ) {
            showToast("Email not valid")
            return false
        } else if (binding.edtPhone.text.toString().trim().isEmpty()) {
            showToast("Please Enter phone")
            return false
        } else if (binding.edtPassword.text.toString().length < 6) {
            showToast("Password must have at least 6 characters")
            return false
        } else if (binding.edtPassword.text.toString().trim().isEmpty()) {
            showToast("Please Enter Password")
            return false
        } else if (binding.edtConfirmPassword.text.toString().trim().isEmpty()) {
            showToast("Please Enter Confirm Password")
            return false
        } else if (binding.edtConfirmPassword.text.toString()
                .trim() != binding.edtPassword.text.toString().trim()
        ) {
            showToast("Password and Confirm Password must be the same")
            return false
        }
        signUp()
        return true
    }


    private fun signUp() {
        isLoading(true)

        auth.createUserWithEmailAndPassword(
            binding.edtEmail.text.toString().trim(),
            binding.edtPassword.text.toString().trim()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    isLoading(false)
                    val passwordHashed = Bcrypt.hash(
                        binding.edtPassword.text.toString().trim(),
                        SALT_ROUNDS
                    )

                    val user = User(
                        email = binding.edtEmail.text.toString().trim(),
                        phone = binding.edtPhone.text.toString().trim(),
                        fullName = binding.edtFullName.text.toString().trim(),
                        password = passwordHashed
                    )

                    runBlocking {
                        val file = File(filesDir, "my_file.bin")
                        withContext(Dispatchers.IO) {
                            FileOutputStream(file).use { output ->
                                output.write(passwordHashed)
                            }
                        }
                    }
                    preferenceManager.putString(KEY_USER_EMAIL, binding.edtEmail.text.toString());
                    preferenceManager.putBoolean(KEY_IS_SIGNED_IN, true)
                    preferenceManager.putString(KEY_USER_ID, "1")
                    preferenceManager.putString(
                        KEY_USER_FULL_NAME,
                        binding.edtFullName.text.toString()
                    )
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Toast.makeText(this@SignUpActivity, "Add User Successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // If sign in fails, display a message to the user.
                    isLoading(false)
                    Toast.makeText(
                        this@SignUpActivity,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

    }

    private fun isLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
    }


}