package com.example.tech_mart_application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tech_mart_application.R
import com.example.tech_mart_application.databinding.ActivityChatBotBinding
import com.example.tech_mart_application.viewModel.ChatViewModel

class ChatBotActivity : AppCompatActivity() {

    val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

    private lateinit var binding: ActivityChatBotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            chatViewModel.sendMessage(binding.edtMessage.text.toString())
        }


    }


}