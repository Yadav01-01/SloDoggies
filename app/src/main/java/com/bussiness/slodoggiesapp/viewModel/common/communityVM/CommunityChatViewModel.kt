package com.bussiness.slodoggiesapp.viewModel.common.communityVM

import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.ChatMessage
import com.bussiness.slodoggiesapp.data.model.common.UserImageResponse
import com.bussiness.slodoggiesapp.data.remote.ChatRepository
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CommunityChatViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    // Initial sample messages
    private val _messages = MutableStateFlow(listOf<ChatMessage>())


    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val chatRepository : ChatRepository = ChatRepository(firestore,storage)
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _currentMessage = MutableStateFlow("")
    var currentUserId:String =""
    val currentMessage: StateFlow<String> = _currentMessage



    // holds multiple pending attachments (like WhatsApp allows multiple images)
    private val _pendingAttachments = MutableStateFlow<List<Pair<Uri, String>>>(emptyList())
    val pendingAttachments: StateFlow<List<Pair<Uri, String>>> = _pendingAttachments


    private val _chatId = MutableStateFlow<String?>(null)
    val chatId: StateFlow<String?> = _chatId

    var senderImage :String =""
    private val _receiverId = MutableStateFlow<String?>(null)
    val receiverId: StateFlow<String?> = _receiverId

    fun setChatData(chatId: String, receiverId: String) {
        _chatId.value = chatId
        _receiverId.value = receiverId
    }



    fun onMessageChange(newMessage: String) {
        _currentMessage.value = newMessage.take(250)
    }

    // Handle message send (text + attachments)
    fun sendMessage() {
        val text = _currentMessage.value.trim()
        val attachments = _pendingAttachments.value
        val currentTime = System.currentTimeMillis()

        if (text.isNotBlank() || attachments.isNotEmpty()) {
            if (text.isNotBlank()) {

                val messageStr= _currentMessage.value
                _currentMessage.value =""

                Log.d("Testing_MESSAGE","Message is"+messageStr+" "+
                "Chat Id"+chatId.value+" \n receiver id"+receiverId.value+" \n senderId"+currentUserId+" \nchat id "+chatId.value
                )

                val chatMessage = ChatMessage(senderId =currentUserId, message = messageStr,
                    receiverId = receiverId.value?:"", senderImage = senderImage
                    )

                chatId.value.let {
                    chatRepository.sendMessage(it?:"",chatMessage)
                }
//                _messages.value += ChatMessage(
//                    text = text,
//                    isUser = true,
//                    timestamp = currentTime
//                )
            }


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


/*    fun getMessage(chatId:String,currentUserId:String){
        viewModelScope.launch {
            chatRepository.observeMessages(chatId,currentUserId).collect {
                it.forEach {
                    it.date = formatTimestamp(it.timestamp).first
                    it.time = formatTimestamp(it.timestamp).second
                }
                _messages.value =it
//                _messages.postValue(it)
            }
        }
    }*/
fun getMessage(chatId: String, currentUserId: String) {
    viewModelScope.launch {
        chatRepository.observeMessages(chatId, currentUserId).collect { list ->

            val mappedList = list.map { msg ->
                val (date, time) = formatTimestamp(msg.timestamp)

                msg.copy(
                    date = date,
                    time = time
                )
            }

            _messages.value = mappedList
        }
    }
}


    fun getUserImage(userId: String) {
        viewModelScope.launch {
            repository.getUserImage(userId).collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        result.data.let { response ->
                                 response.data?.let {
                                     senderImage = it
                                 }
                        }
                    }

                    is Resource.Error -> {

                    }

                    Resource.Idle -> TODO()
                }
            }
        }
    }



    fun formatTimestamp(timestamp: Long): Pair<String, String> {
        val date = Date(timestamp)

        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Changed here

        val formattedDate = dateFormat.format(date) // e.g., "14 May 2025"
        val formattedTime = timeFormat.format(date) // e.g., "09:15 PM"

        return Pair(formattedDate, formattedTime)
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
