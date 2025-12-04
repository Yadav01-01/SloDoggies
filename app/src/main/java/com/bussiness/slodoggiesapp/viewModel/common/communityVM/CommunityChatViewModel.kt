package com.bussiness.slodoggiesapp.viewModel.common.communityVM

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.data.model.common.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityChatViewModel @Inject constructor() : ViewModel() {

    // Initial sample messages
    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                text = "Hi! Thank you for reaching out. How can I assist you today?",
                isUser = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2 // 2 hours ago
            ),
            ChatMessage(
                text = "Hello! I am interested in your grooming services.",
                isUser = true,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 // 1 hour ago
            ),
            ChatMessage(
                text = "Sure! We have basic, premium, and deluxe packages. Would you like details?",
                isUser = false,
                timestamp = System.currentTimeMillis() - 1000 * 60 * 45 // 45 mins ago
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _currentMessage = MutableStateFlow("")
    val currentMessage: StateFlow<String> = _currentMessage

    // holds multiple pending attachments (like WhatsApp allows multiple images)
    private val _pendingAttachments = MutableStateFlow<List<Pair<Uri, String>>>(emptyList())
    val pendingAttachments: StateFlow<List<Pair<Uri, String>>> = _pendingAttachments

    // Handle text typing
    fun onMessageChange(newMessage: String) {
        _currentMessage.value = newMessage.take(250)
    }

    // Handle message send (text + attachments)
    fun sendMessage() {
        val text = _currentMessage.value.trim()
        val attachments = _pendingAttachments.value
        val currentTime = System.currentTimeMillis()

        if (text.isNotBlank() || attachments.isNotEmpty()) {

            // Send text message (if present)
            if (text.isNotBlank()) {
                _messages.value += ChatMessage(
                    text = text,
                    isUser = true,
                    timestamp = currentTime
                )
            }

            // Send attachments (if any)
            if (attachments.isNotEmpty()) {
                attachments.forEach { attachment ->
                    _messages.value += ChatMessage(
                        text = if (text.isBlank()) "" else text,
                        isUser = true,
                        attachmentUri = attachment.first,
                        attachmentType = attachment.second,
                        timestamp = currentTime
                    )
                }
            }

            // Clear input & attachments
            _currentMessage.value = ""
            _pendingAttachments.value = emptyList()
        }
    }

    // Add attachment (image/video/pdf etc.)
    fun addAttachment(uri: Uri, type: String) {
        _pendingAttachments.value += Pair(uri, type)
    }

    // Remove specific attachment
    fun removeAttachment(uri: Uri) {
        _pendingAttachments.value = _pendingAttachments.value.filterNot { it.first == uri }
    }
}
