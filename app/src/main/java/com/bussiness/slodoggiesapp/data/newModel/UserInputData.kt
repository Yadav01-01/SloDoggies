package com.bussiness.slodoggiesapp.data.newModel

data class UserInputData(
    val name: String,
    val contact: String,
    val contactType: String?,
    val password: String,
    val fcmToken: String
)
