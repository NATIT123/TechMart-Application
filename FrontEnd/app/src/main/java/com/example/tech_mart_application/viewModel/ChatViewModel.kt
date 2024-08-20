package com.example.tech_mart_application.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tech_mart_application.models.MessageModel
import com.example.tech_mart_application.utils.Constants
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch
import java.util.Date

class ChatViewModel : ViewModel() {
    private val messageList = mutableListOf<MessageModel>()

    private val messageLiveData = MutableLiveData<MutableList<MessageModel>>()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = Constants.API_KEY_GEMINI
    )

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }
                )
                messageList.add(MessageModel(message, "user", Date()))
                messageList.add(MessageModel("Typing....", "model", Date()))
                val response = chat.sendMessage(message)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(), "model", Date()))
                messageLiveData.postValue(messageList)
            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error: ${e.message.toString()}", "model", Date()))
            }
        }
    }

    fun observerMessage(): MutableLiveData<MutableList<MessageModel>> {
        return messageLiveData
    }


}