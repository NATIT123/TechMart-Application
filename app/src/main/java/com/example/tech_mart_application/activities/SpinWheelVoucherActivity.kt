package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivitySpinWheelVoucherBinding
import java.util.Random

class SpinWheelVoucherActivity : AppCompatActivity() {


    private val listValue = mutableListOf<Int>()
    private lateinit var sectorDegrees: IntArray

    private var randomIndex = 0
    private var spinning = false
    private var earningRecord = 0
    private val random = Random()

    private lateinit var binding: ActivitySpinWheelVoucherBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySpinWheelVoucherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listValue.addAll(intArrayOf(100, 1000, 400, 800, 7000, 5000, 300, 2000).toList())
        sectorDegrees = IntArray(listValue.size)

        generatorValue()

        binding.wheel.setOnClickListener {
            if (!spinning) {
                spin()
                spinning = true
            }
        }
    }

    private fun generatorValue() {
        val sectorDegree = 360 / listValue.size
        for (value in 0..<listValue.size) {
            sectorDegrees[value] = (value + 1) * sectorDegree
        }
    }

    private fun spin() {
        randomIndex = random.nextInt(listValue.size)

        val randomDegree = generateRandomDegreeToSpinTo()

        val rotateAnimation =
            RotateAnimation(
                0F,
                randomDegree.toFloat(),
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )


        //Time For Spinning
        rotateAnimation.duration = 360
        rotateAnimation.fillAfter = true

        //interpolator
        rotateAnimation.interpolator = DecelerateInterpolator()

        //Spinning Listener
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                val earnedCoins = listValue[listValue.size - (randomIndex + 1)]

                saveEarnings(earnedCoins)


                spinning = false
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        binding.wheel.startAnimation(rotateAnimation)


    }

    private fun saveEarnings(earnedCoins: Int) {
        earningRecord += earnedCoins
        binding.tvEarning.text = earningRecord.toString()
    }

    private fun generateRandomDegreeToSpinTo(): Int {
        return 360 * listValue.size + sectorDegrees[randomIndex]
    }
}