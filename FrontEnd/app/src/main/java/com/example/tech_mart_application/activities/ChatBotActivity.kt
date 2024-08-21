package com.example.tech_mart_application.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_mart_application.R
import com.example.tech_mart_application.adapters.ChatBotViewAdapter
import com.example.tech_mart_application.databinding.ActivityChatBotBinding
import com.example.tech_mart_application.models.MessageModel
import com.example.tech_mart_application.viewModel.ChatViewModel


class ChatBotActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChatBotBinding
    private lateinit var chatBotViewModel: ChatViewModel
    private lateinit var mChatBotViewAdapter: ChatBotViewAdapter
    private var listMessage = mutableListOf<MessageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatBotViewModel = ViewModelProvider(this)[ChatViewModel::class.java]


        binding.btnSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(
                    this@ChatBotActivity,
                    "Please input your message",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                chatBotViewModel.sendMessage(binding.edtMessage.text.toString())
                binding.edtMessage.setText("")
            }
        }

        loadRecyclerView()

        //Back Button
        binding.btnBack.setOnClickListener {
            finish()
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadRecyclerView() {
        listMessage.clear()
        binding.isEmpty = listMessage.isEmpty()
        mChatBotViewAdapter = ChatBotViewAdapter()
        chatBotViewModel.observerMessage().observe(this@ChatBotActivity) { it ->
            listMessage = it
            binding.isEmpty = listMessage.isEmpty()
            Log.d("MyApp",binding.isEmpty.toString())
            if (listMessage.isNotEmpty()) {
                listMessage.sortedWith(compareBy { it.date })
                mChatBotViewAdapter.diff.submitList(listMessage)
                mChatBotViewAdapter.notifyItemRangeChanged(
                    listMessage.size,
                    listMessage.size
                )
                binding.rcvChat.apply {
                    layoutManager = LinearLayoutManager(this@ChatBotActivity)
                    adapter = mChatBotViewAdapter
                }
                binding.rcvChat.smoothScrollToPosition(listMessage.size - 1)
            }

        }
    }


}