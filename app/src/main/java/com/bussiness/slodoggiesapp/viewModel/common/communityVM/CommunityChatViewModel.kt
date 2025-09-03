package com.bussiness.slodoggiesapp.viewModel.common.communityVM

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.model.common.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CommunityChatViewModel @Inject constructor() : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage("Hi! Thank you for reaching out. How can I assist you today?", false),
            ChatMessage("Hello! I am interested in your grooming services.", true),
            ChatMessage("Sure! We have basic, premium, and deluxe packages. Would you like details?", false)
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _currentMessage = MutableStateFlow("")
    val currentMessage: StateFlow<String> = _currentMessage

    fun onMessageChange(newMessage: String) {
        _currentMessage.value = newMessage
    }

    fun sendMessage() {
        val text = _currentMessage.value.trim()
        if (text.isNotBlank()) {
            _messages.value += ChatMessage(text, true)
            _currentMessage.value = ""
        }
    }
}
