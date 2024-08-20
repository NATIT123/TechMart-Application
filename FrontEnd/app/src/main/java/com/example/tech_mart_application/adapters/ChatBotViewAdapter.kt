package com.example.tech_mart_application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tech_mart_application.databinding.ActivityCreateUserBinding
import com.example.tech_mart_application.databinding.LayoutMessageModelBinding
import com.example.tech_mart_application.databinding.LayoutMessageUserBinding
import com.example.tech_mart_application.models.MessageModel
import com.example.tech_mart_application.utils.Constants.Companion.VIEW_TYPE_MODEL
import com.example.tech_mart_application.utils.Constants.Companion.VIEW_TYPE_USER
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatBotViewAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class UserMessageViewHolder(val layoutMessageUserBinding: LayoutMessageUserBinding) :
        RecyclerView.ViewHolder(layoutMessageUserBinding.root)

    inner class ModelMessageViewHolder(val layoutMessageModelBinding: LayoutMessageModelBinding) :
        RecyclerView.ViewHolder(layoutMessageModelBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.date == newItem.date
        }
    }

    val diff = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val bindingFeature = LayoutMessageUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                UserMessageViewHolder(bindingFeature)
            }

            else -> {
                val bindingFeature = LayoutMessageModelBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ModelMessageViewHolder(bindingFeature)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = diff.currentList[position]
        if (chatMessage.role == "user") {
            return VIEW_TYPE_USER
        }
        return VIEW_TYPE_MODEL
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    private fun convertDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy - hh::mm::ss ", Locale.getDefault()).format(date)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = diff.currentList[position]
        if (getItemViewType(position) == VIEW_TYPE_USER) {
            val userViewHolder = holder as UserMessageViewHolder
            userViewHolder.layoutMessageUserBinding.apply {
                tvUserMessage.text = chatMessage.message
                tvDateTime.text = convertDate(chatMessage.date)
            }
        } else {
            val modelViewHolder = holder as ModelMessageViewHolder
            modelViewHolder.layoutMessageModelBinding.apply {
                tvModelMessage.text = chatMessage.message
                tvDateTime.text = convertDate(chatMessage.date)
            }
        }
    }
}