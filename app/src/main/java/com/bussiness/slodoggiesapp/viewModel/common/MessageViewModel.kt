package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.model.common.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MessageViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    private val _messages = MutableStateFlow(dummyMessages)
    val messages: StateFlow<List<Message>> = _messages



}

private val dummyMessages = listOf(
    Message(
        profileImageUrl = "https://example.com/user1.jpg",
        username = "Merry",
        time = "14:41",
        description = "I hope it goes well."
    ),
    Message(
        profileImageUrl = "https://example.com/user2.jpg",
        username = "SLO K9 Spa",
        time = "15:41",
        description = "So, what's your plan this weekend?"
    )
)