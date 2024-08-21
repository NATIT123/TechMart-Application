package com.example.tech_mart_application

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tech_mart_application.activities.ChatBotActivity
import com.example.tech_mart_application.database.ProductDatabase
import com.example.tech_mart_application.databinding.ActivityMainBinding
import com.example.tech_mart_application.viewModel.ProductViewModel
import com.example.tech_mart_application.viewModel.ProductViewModelFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle


    val viewModel: ProductViewModel by lazy {
        val productDatabase = ProductDatabase.getInstance(this)
        val productViewModelFactory = ProductViewModelFactory(productDatabase, applicationContext)
        ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.i("TAG", "handleOnBackPressed: Exit")
                        exitOnBackPressed()
                    }
                })
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_frame) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.myBottomNav, navController)

        binding.navigationView.setNavigationItemSelectedListener(this)

        //Chat Bot
        binding.chatbot.setOnClickListener {
            val intent = Intent(this@MainActivity, ChatBotActivity::class.java)
            startActivity(intent)
        }
    }



    private fun exitOnBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setTitle("Confirm Exit")
                setMessage("Are you sure you want to exist?")
                setCancelable(false)
                setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                create()
                show()
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.wishlist->{
                Toast.makeText(this@MainActivity, "Test", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                Toast.makeText(this@MainActivity, "Test", Toast.LENGTH_SHORT).show()
                 false
            }
        }
    }
}