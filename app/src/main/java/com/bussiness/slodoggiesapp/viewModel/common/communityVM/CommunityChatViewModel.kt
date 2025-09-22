package com.bussiness.slodoggiesapp.viewModel.common.communityVM

import android.net.Uri
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

    // holds multiple pending attachments (like WhatsApp allows multiple images)
    private val _pendingAttachments = MutableStateFlow<List<Pair<Uri, String>>>(emptyList())
    val pendingAttachments: StateFlow<List<Pair<Uri, String>>> = _pendingAttachments

    fun onMessageChange(newMessage: String) {
        _currentMessage.value = newMessage.take(250)
    }

    fun sendMessage() {
        val text = _currentMessage.value.trim()
        val attachments = _pendingAttachments.value

        // if user typed something or attached files
        if (text.isNotBlank() || attachments.isNotEmpty()) {
            if (attachments.isEmpty()) {
                // only text
                _messages.value += ChatMessage(text = text, isUser = true)
            } else {
                // send attachments (with or without text)
                attachments.forEachIndexed { index, attachment ->
                    _messages.value += ChatMessage(
                        text = text,
                        isUser = true,
                        attachmentUri = attachment.first,
                        attachmentType = attachment.second
                    )
                }
            }

            // clear after send
            _currentMessage.value = ""
            _pendingAttachments.value = emptyList()
        }
    }

    fun addAttachment(uri: Uri, type: String) {
        _pendingAttachments.value += Pair(uri, type)
    }

    fun removeAttachment(uri: Uri) {
        _pendingAttachments.value = _pendingAttachments.value.filterNot { it.first == uri }
    }
}

