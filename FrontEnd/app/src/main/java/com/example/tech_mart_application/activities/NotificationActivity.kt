package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.MainActivity
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.NotificationViewAdapter
import com.example.tech_mart_application.databinding.ActivityNotificationBinding
import com.example.tech_mart_application.models.Notification
import com.example.tech_mart_application.viewModel.ProductViewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private var listNotification = mutableListOf<Notification>()
    private lateinit var mNotificationViewAdapter: NotificationViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDataNotification()
        loadRecyclerView()
        
        //Button Back
        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun loadRecyclerView() {
        binding.isLoading = true
        binding.rcvNotification.apply {
            binding.isEmpty = listNotification.isEmpty()
            if (listNotification.isNotEmpty()) {
                mNotificationViewAdapter = NotificationViewAdapter(listNotification)
                binding.rcvNotification.apply {
                    adapter = mNotificationViewAdapter
                    layoutManager = LinearLayoutManager(
                        this@NotificationActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }
            }
        }
        binding.isLoading = false
    }


    private fun loadDataNotification() {
        listNotification.add(
            Notification(
                noType = "Notification1",
                noDate = "16/8/2024",
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                noType = "Notification1",
                noDate = "16/8/2024",
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                noType = "Notification1",
                noDate = "16/8/2024",
                isRead = false
            )
        )
        listNotification.add(
            Notification(
                noType = "Notification1",
                noDate = "16/8/2024",
                isRead = false
            )
        )
    }
}