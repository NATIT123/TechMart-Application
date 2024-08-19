package com.example.tech_mart_application.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tech_mart_application.models.MessageModel
import com.example.tech_mart_application.utils.Constants
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val messageList = mutableListOf<MessageModel>()

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = Constants.API_KEY_GEMINI
    )

    fun sendMessage(message: String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat()
            messageList.add(MessageModel(message, "user"))
            val response = chat.sendMessage(message)
            messageList.add(MessageModel(response.text.toString(), "model"))
        }
    }
}