package com.bussiness.slodoggiesapp.data.model.common

data class AddParticipant(
    val id: String,
    val name: String,
    val profileImage: String? = null,
    val isSelected: Boolean = false
)

