package com.bussiness.slodoggiesapp.model.common

sealed class ChatWindow {
    data class Text(val id: String, val content: String, val isUser: Boolean) : ChatWindow()
    data class Feedback(
        val id: String,
        val serviceFeedback: List<String>, // e.g., ["Service quality", "Communication"]
        val isSubmitted: Boolean = false,
        val userRatings: List<Int> = listOf(0, 0), // 0 = no rating, 1-5 = stars
        val feedbackText: String = ""
    ) : ChatWindow()
}
