package com.example.tech_mart_application.activities

import android.app.Activity
import android.content.Intent
import android.credentials.CredentialManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.Retrofit.ApiService
import com.example.tech_mart_application.databinding.ActivitySignInBinding
import com.example.tech_mart_application.models.DataDetailResponse
import com.example.tech_mart_application.models.DataResponse
import com.example.tech_mart_application.models.User
import com.example.tech_mart_application.utils.Constants
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ADDRESS
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_EMAIL
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_FULL_NAME
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_ID
import com.example.tech_mart_application.utils.Constants.Companion.KEY_USER_IMAGE
import com.example.tech_mart_application.utils.Constants.Companion.SALT_ROUNDS
import com.example.tech_mart_application.utils.PreferenceManager
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private var showPassword: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuth(account.idToken!!)
                } catch (e: Exception) {
                    showToast(e.message.toString())
                }
            }
        }

    private fun firebaseAuth(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userAuth = auth.currentUser
                val user = User(
                    userAuth?.uid,
                    userAuth?.photoUrl.toString(),
                    userAuth?.email!!,
                    userAuth.phoneNumber ?: "unregister",
                    userAuth.displayName!!,
                    "", "Empty"
                )
                handleLoginGoogle(user)
            } else {
                showToast(task.result.toString())
            }
        }

    }


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

        //Google Sign In

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        gsc = GoogleSignIn.getClient(this@SignInActivity, gso)

        binding.btnGoogle.setOnClickListener {
            isLoading(false)
            handleSignInGoogle()
        }


    }

    private fun handleSignInGoogle() {
        val signInIntent = gsc.signInIntent
        resultLauncher.launch(signInIntent)
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
        val user = User(
            email = email,
            password = password,
            image = "",
            phone = "",
            fullName = "",
            address = ""
        )
        handleLoginUser(user)

    }

    private fun handleLoginGoogle(user: User) {
        ApiService.apiService.detailUser(user.email).enqueue(object : Callback<DataDetailResponse> {
            override fun onResponse(
                call: Call<DataDetailResponse>,
                response: Response<DataDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        when (body.status) {
                            "ok" -> {
                                isLoading(false)
                                val user = body.data

                                Log.d("MyApp", user.image)


                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                                preferenceManager.putString(
                                    KEY_USER_ID, user.id!!
                                )
                                preferenceManager.putString(
                                    KEY_USER_EMAIL,
                                    user.email
                                )
                                preferenceManager.putString(
                                    KEY_USER_FULL_NAME,
                                    user.email
                                )
                                preferenceManager.putString(KEY_USER_IMAGE, user.image)
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                showToast("Login Successfully")
                            }

                            "notok" -> {
                                handleApiRegisterByGoogle(user)
                            }

                            else -> {
                                handleDataErrorGoogle(body)
                            }
                        }
                    } else {
                        isLoading(false)
                        showToast(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<DataDetailResponse>, error: Throwable) {
                isLoading(false)
                showToast(error.message.toString())
            }

        })
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

    private fun handleDataErrorGoogle(dataDetailResponse: DataDetailResponse) {
        isLoading(false)
        showToast(dataDetailResponse.message)
    }


    private fun handleApiRegisterByGoogle(user: User) {
        ApiService.apiService.registerUserByGoogle(user).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        when (body.status) {
                            "ok" -> {
                                val passwordHashed = Bcrypt.hash(
                                    user.password,
                                    SALT_ROUNDS
                                )
                                runBlocking {
                                    val file = File(filesDir, "my_file.bin")
                                    withContext(Dispatchers.IO) {
                                        FileOutputStream(file).use { output ->
                                            output.write(passwordHashed)
                                        }
                                    }
                                }

                                preferenceManager.putString(
                                    KEY_USER_EMAIL,
                                    user.email
                                );
                                preferenceManager.putString(
                                    KEY_USER_ADDRESS, user.address
                                )
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                                preferenceManager.putString(KEY_USER_ID, body.data.split(':')[1])
                                preferenceManager.putString(
                                    KEY_USER_FULL_NAME,
                                    user.fullName
                                )
                                preferenceManager.putString(KEY_USER_IMAGE, user.image)
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                Toast.makeText(
                                    this@SignInActivity,
                                    "Register Successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
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
}