package com.example.tech_mart_application.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.room.util.getColumnIndexOrThrow
import com.example.tech_mart_application.R
import com.example.tech_mart_application.utils.Constants.Companion.IS_STARTED
import com.example.tech_mart_application.utils.PreferenceManager

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (preferenceManager.getBoolean(IS_STARTED)) {
//                val intent = Intent(this@SplashScreenActivity, ChangePasswordActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
                goToSignInActivity()
            } else {
                goToOnBoardingActivity()
            }
        }, 3000)
    }

    private fun goToSignInActivity() {
        val intent = Intent(this@SplashScreenActivity, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToOnBoardingActivity() {
        val intent = Intent(this@SplashScreenActivity, OnboardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


}