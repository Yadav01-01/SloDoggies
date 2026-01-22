package com.bussiness.slodoggiesapp.viewModel.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.AllChatListData
import com.bussiness.slodoggiesapp.data.model.common.AllChatListResponse
import com.bussiness.slodoggiesapp.data.model.common.Message
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.remote.ChatRepository
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.ChatListUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
/*
@HiltViewModel
class MessageViewModel @Inject constructor( private val repository: Repository, private var sessionManager: SessionManager) : ViewModel() {
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val chatRepository : ChatRepository = ChatRepository(firestore,storage)
    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState

    // Store API data and Firebase data separately
    private val _apiChatData = MutableStateFlow<List<AllChatListData>?>(null)
    private val _firebaseChatData = MutableStateFlow<List<AllChatListData>?>(null)
    init {
        loadChatsFromFirebase()
        allChatList(userId = sessionManager.getUserId())
    }
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    private val _messages = MutableStateFlow(dummyMessages)
    val messages: StateFlow<List<Message>> = _messages

    /*fun allChatList(userId :String) {
        viewModelScope.launch {
            repository.allChatList(userId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data?.let { response ->
                            if (response.success == true) {
                                _uiState.value = _uiState.value.copy(
                                    data = response.data as MutableList<AllChatListData>?
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    error = response.message
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }*/


   /* private fun loadChatsFromFirebase() {
        viewModelScope.launch {
            val currentUserId = sessionManager.getUserId()

            chatRepository.observeAllChats(currentUserId).collect { chatSummaries ->
                // Fetch user details for each chat
                val chatListData = mutableListOf<AllChatListData>()

                chatSummaries.forEach { summary ->
                    // Get user details for other user
                    val userDetails = chatRepository.getUserDetails(summary.otherUserId)

                    chatListData.add(
                        AllChatListData(
//                            chat_id = summary.chatId,
//                            user_id = summary.otherUserId.toIntOrNull() ?: 0,
//                            user_name = userDetails?.name ?: "Unknown User",
//                            profile_image = userDetails?.profileImage ?: "",
//                            description = summary.lastMessage,
//                            time = getRelativeTime(summary.lastMessageTime),
                            // Add new fields for Firebase data
                            last_message = summary.lastMessage,
                            last_message_time = summary.lastMessageTime
                        )
                    )
                }

                _uiState.value = _uiState.value.copy(
                    data = chatListData,
                    isLoading = false
                )
            }
        }
    }*/

    fun allChatList(userId :String) {
        viewModelScope.launch {
            repository.allChatList(userId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.let { response ->
                            if (response.success == true) {
                                // Store API data
                                _apiChatData.value = response.data

                                // Merge with Firebase data if available
                                mergeChatData()
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    error = response.message
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    private fun loadChatsFromFirebase() {
        viewModelScope.launch {
            val currentUserId = sessionManager.getUserId()

            chatRepository.observeAllChats(currentUserId).collect { chatSummaries ->
                // Store Firebase data
                _firebaseChatData.value = chatSummaries

                // Merge with API data if available
                mergeChatData()
            }
        }
    }

    private fun mergeChatData() {
        val apiData = _apiChatData.value ?: emptyList()
        val firebaseData = _firebaseChatData.value ?: emptyList()

        Log.d("ChatMerge", "API Data count: ${apiData.size}")
        Log.d("ChatMerge", "Firebase Data count: ${firebaseData.size}")

        if (apiData.isEmpty() && firebaseData.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                data = mutableListOf(),
                isLoading = false
            )
            return
        }

        // Create a map for quick lookup
        val firebaseMap = firebaseData.associateBy { it.chatId }

        val mergedList = mutableListOf<AllChatListData>()

        // First, process API data and enhance with Firebase last messages
        apiData.forEach { apiChat ->
            val chatId = apiChat.chat_id ?: ""
            val firebaseChat = firebaseMap[chatId]

            if (firebaseChat != null) {
                // Match found! Merge API user info with Firebase last message
                val mergedChat = apiChat.copy(
                    last_message = firebaseChat.lastMessage,
                    last_message_time = firebaseChat.lastMessageTime,
                    time = getRelativeTime(firebaseChat.lastMessageTime),
                    description = firebaseChat.lastMessage
                )
                mergedList.add(mergedChat)
                Log.d("ChatMerge", "Merged chat: $chatId")
            } else {
                // API chat not found in Firebase, use API data as is
                mergedList.add(apiChat)
                Log.d("ChatMerge", "API only chat: $chatId")
            }
        }

        // Now add Firebase chats that don't exist in API
        // But we need user info for these, so we might need to fetch from users collection
        firebaseData.forEach { firebaseChat ->
            val chatId = firebaseChat.chatId
            if (mergedList.none { it.chat_id == chatId }) {
                // Try to extract user ID from chat ID (assuming format "user1_user2")
                val userId = extractUserIdFromChatId(chatId, sessionManager.getUserId())

                val firebaseOnlyChat = AllChatListData(
                    chat_id = chatId,
                    user_id = userId.toIntOrNull() ?: 0,
                    user_name = "User $userId", // You might want to fetch actual name
                    last_message = firebaseChat.lastMessage,
                    last_message_time = firebaseChat.lastMessageTime,
                    time = getRelativeTime(firebaseChat.lastMessageTime),
                    description = firebaseChat.lastMessage
                )
                mergedList.add(firebaseOnlyChat)
                Log.d("ChatMerge", "Firebase only chat: $chatId")
            }
        }

        // Sort by last message time (newest first)
        val sortedList = mergedList.sortedByDescending {
            it.last_message_time ?: 0L
        }

        Log.d("ChatMerge", "Final merged count: ${sortedList.size}")

        _uiState.value = _uiState.value.copy(
            data = sortedList.toMutableList(),
            isLoading = false
        )
    }

    // Helper function to extract user ID from chat ID like "52_18"
    private fun extractUserIdFromChatId(chatId: String, currentUserId: String): String {
        val parts = chatId.split("_")
        if (parts.size >= 2) {
            // Assuming format: "user1_user2" where one is current user
            return parts.firstOrNull { it != currentUserId } ?: parts.firstOrNull() ?: ""
        }
        return ""
    }

    private fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60000 -> "Just now"
            diff < 3600000 -> "${diff / 60000} min ago"
            diff < 86400000 -> "${diff / 3600000} hr ago"
            diff < 172800000 -> "Yesterday"
            else -> {
                val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                sdf.format(Date(timestamp))
            }
        }
    }
}
*/
@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val chatRepository : ChatRepository = ChatRepository(firestore,storage)

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState

    // Store API data and Firebase data separately
    private val _apiChatData = MutableStateFlow<List<AllChatListData>?>(null)
    private val _firebaseChatData = MutableStateFlow<List<ChatRepository.ChatSummary>?>(null) // Change type here

    init {
        loadChatsFromFirebase()
        allChatList(userId = sessionManager.getUserId())
    }

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    private val _messages = MutableStateFlow(dummyMessages)
    val messages: StateFlow<List<Message>> = _messages


    fun allChatList(userId :String) {
        viewModelScope.launch {
            repository.allChatList(userId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.let { response ->
                            if (response.success == true) {
                                // Store API data
                                _apiChatData.value = response.data

                                // Merge with Firebase data if available
                                mergeChatData()
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    error = response.message
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    private fun loadChatsFromFirebase() {
        viewModelScope.launch {
            val currentUserId = sessionManager.getUserId()

            chatRepository.observeAllChats(currentUserId).collect { chatSummaries ->
                // Store Firebase data (ChatSummary type)
                _firebaseChatData.value = chatSummaries

                // Merge with API data if available
                mergeChatData()
            }
        }
    }

    private fun mergeChatData() {
        val apiData = _apiChatData.value ?: emptyList()
        val firebaseData = _firebaseChatData.value ?: emptyList()

        Log.d("ChatMerge", "API Data count: ${apiData.size}")
        Log.d("ChatMerge", "Firebase Data count: ${firebaseData.size}")
        Log.d("ChatMerge", "Firebase Data count: ${firebaseData.size}")

        if (apiData.isEmpty() && firebaseData.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                data = mutableListOf(),
                isLoading = false
            )
            return
        }

        // Create a map for quick lookup - Use ChatSummary properties
        val firebaseMap = firebaseData.associateBy { it.chatId }

        val mergedList = mutableListOf<AllChatListData>()

        // First, process API data and enhance with Firebase last messages
        apiData.forEach { apiChat ->
            val chatId = apiChat.chat_id ?: ""
            val firebaseChat = firebaseMap[chatId]

            if (firebaseChat != null) {
                // Match found! Merge API user info with Firebase last message
                val mergedChat = apiChat.copy(
                    last_message = firebaseChat.lastMessage,
                    last_message_time = firebaseChat.lastMessageTime,
                    time = getRelativeTime(firebaseChat.lastMessageTime),
                    description = firebaseChat.lastMessage,
                    unread_count = firebaseChat.totalUnSeenMessageCount
                )
                mergedList.add(mergedChat)
                Log.d("ChatMerge", "Merged chat: $chatId")
            }

        }



        // Sort by last message time (newest first)
        val sortedList = mergedList.sortedByDescending {
            it.last_message_time ?: 0L
        }

        Log.d("ChatMerge", "Final merged count: ${sortedList.size}")

        _uiState.value = _uiState.value.copy(
            data = sortedList.toMutableList(),
            isLoading = false
        )
    }


    private fun getRelativeTime(timestamp: Long): String {
        if (timestamp == 0L) return ""

        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60000 -> "Just now"
            diff < 3600000 -> "${diff / 60000} min ago"
            diff < 86400000 -> "${diff / 3600000} hr ago"
            diff < 172800000 -> "Yesterday"
            else -> {
                val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                sdf.format(Date(timestamp))
            }
        }
    }
}
private val dummyMessages = listOf(
    Message(
        profileImageUrl = "https://example.com/user1.jpg",
        username = "Merry",
        time = "14:41",
        description = "I hope it goes well.",
        count = "2"
    ),
    Message(
        profileImageUrl = "https://example.com/user2.jpg",
        username = "SLO K9 Spa",
        time = "15:41",
        description = "So, what's your plan this weekend?",
        count = "1"

    )
)