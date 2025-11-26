package com.bussiness.slodoggiesapp.data.newModel.servicelist

data class Data(
    val business_user_id: Int=0,
    val created_at: String?=null,
    val description: String?=null,
    val id: Int=0,
    val price: String?=null,
    val service_image: MutableList<String>?=null,
    val service_title: String?=null,
    val updated_at: String?=null
)