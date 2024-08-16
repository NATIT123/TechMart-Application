package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.ActivityNotificationBinding
import com.example.tech_mart_application.databinding.LayoutItemNotificationBinding
import com.example.tech_mart_application.models.Notification

class NotificationViewAdapter(private val listNotification: MutableList<Notification>) :
    RecyclerView.Adapter<NotificationViewAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val layoutItemNotificationBinding: LayoutItemNotificationBinding) :
        RecyclerView.ViewHolder(layoutItemNotificationBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNotification.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = listNotification[position]
        holder.layoutItemNotificationBinding.apply {
            notifications = notification
        }
    }
}