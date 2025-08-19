package com.bussiness.slodoggiesapp.model.petOwner

import androidx.compose.ui.graphics.Color

data class AddParticipant(val id: String,
                          val name: String,
                          val profileImage: Int? = null, // Resource ID for profile image
                          val backgroundColor: Color = Color.Gray
)
