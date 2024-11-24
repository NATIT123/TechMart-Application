package com.example.tech_mart_application.activities.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tech_mart_application.activities.ProfileActivity
import com.example.tech_mart_application.activities.SignInActivity
import com.example.tech_mart_application.databinding.ActivityAdminBinding
import com.example.tech_mart_application.utils.Constants.Companion.IS_STARTED
import com.example.tech_mart_application.utils.PreferenceManager

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)
        preferenceManager.instance()


        //Add Product
        binding.btnAddProduct.setOnClickListener {
            val intent = Intent(this@AdminActivity, AddProductActivity::class.java)
            startActivity(intent)
        }

        //AllProduct
        binding.AllItemProduct.setOnClickListener {
            val intent = Intent(this@AdminActivity, AllProductActivity::class.java)
            startActivity(intent)
        }

        //Profile
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this@AdminActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        //AddNewUser
        binding.CreateNewUser.setOnClickListener {
            val intent = Intent(this@AdminActivity, CreateUserActivity::class.java)
            startActivity(intent)
        }


        //Order Dispatch
        binding.btnOrderDispatch.setOnClickListener {
            val intent = Intent(this@AdminActivity, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }

        //Logout
        binding.btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(
                this@AdminActivity
            )
            dialog.apply {
                setTitle("Confirm Logout")
                setMessage("Are you sure you want to logout?")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    preferenceManager.clear()
                    preferenceManager.putBoolean(IS_STARTED, true);
                    finishAffinity()
                    val intent = Intent(this@AdminActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                create()
                show()
            }
        }


        //Pending order
        binding.btnPendingOrder.setOnClickListener {
            val intent = Intent(this@AdminActivity, PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}