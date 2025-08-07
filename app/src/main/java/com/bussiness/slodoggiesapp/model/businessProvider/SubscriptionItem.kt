package com.bussiness.slodoggiesapp.model.businessProvider

data class SubscriptionData(
    val planName: String,
    val price: String,
    val description: String,
    val isSelected: Boolean,
    val features: List<String>,
    val isActivated: Boolean,
)
