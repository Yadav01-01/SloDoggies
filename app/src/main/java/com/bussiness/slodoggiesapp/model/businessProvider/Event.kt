package com.bussiness.slodoggiesapp.model.businessProvider

data class Event(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val dateTime: String,
    val duration: String,
    val location: String,
    val buttonText: String? = null
)

