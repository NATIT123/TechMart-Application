package com.example.tech_mart_application.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityOnBroadingBinding
import com.example.tech_mart_application.models.SliderInfo
import com.example.tech_mart_application.utils.Constants.Companion.IS_STARTED
import com.example.tech_mart_application.utils.PreferenceManager


class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBroadingBinding
    private val mListSliderInfo = mutableListOf<SliderInfo>()
    private lateinit var mSliderLayout: SliderLayout
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBroadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()

        //Load Slider Info
        loadSliderInfo()


        binding.btnSkip.setOnClickListener {
            onClickButtonSkip()
        }

        binding.btnBack.setOnClickListener {
            onClickButtonBack()
        }

        binding.btnNext.setOnClickListener {
            onClickButtonNext()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun onClickButtonNext() {
        Toast.makeText(this@OnboardingActivity, "Test", Toast.LENGTH_SHORT).show()
        binding.btnBack.visibility = View.VISIBLE
        if (getCurrentItem() == 3) {
            preferenceManager.putBoolean(IS_STARTED, true)
            val intent = Intent(this@OnboardingActivity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            binding.containerPager.currentItem = getCurrentItem() + 1
            if (getCurrentItem() == 3) {
                binding.tvNext.text = "GET STARTED"
            }
        }
    }

    private fun onClickButtonBack() {
        if (getCurrentItem() > 0) {
            binding.containerPager.currentItem = getCurrentItem() - 1
        }
    }

    private fun onClickButtonSkip() {
        preferenceManager.putBoolean(IS_STARTED, true)
        val intent = Intent(this@OnboardingActivity, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun loadSliderInfo() {
        mListSliderInfo.add(SliderInfo(R.drawable.image1, R.string.heading_one, R.string.desc_one))
        mListSliderInfo.add(SliderInfo(R.drawable.image2, R.string.heading_two, R.string.desc_two))
        mListSliderInfo.add(
            SliderInfo(
                R.drawable.image3,
                R.string.heading_three,
                R.string.desc_three
            )
        )
        mListSliderInfo.add(
            SliderInfo(
                R.drawable.image4,
                R.string.heading_fourth,
                R.string.desc_fourth
            )
        )
        mSliderLayout = SliderLayout(this.mListSliderInfo)
        binding.containerPager.apply {
            offscreenPageLimit = 1
            adapter = mSliderLayout
        }

        binding.circleIndicator.setViewPager(binding.containerPager)
        mSliderLayout.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
    }

    private fun getCurrentItem(): Int {
        return binding.containerPager.currentItem
    }
}