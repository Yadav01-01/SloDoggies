package com.bussiness.slodoggiesapp.data.newModel.businessprofile

import android.net.Uri

data class Data(
    val address: String ?=null,
    val available_days: MutableList<String>? = null,
    val available_time: String?=null,
    val business_logo: String?=null,
    val businessLogo: Uri?=null,
    val business_name: String?=null,
    val category: MutableList<String>? = null,
    val city: String?=null,
    val created_at: String ?=null,
    val email: String?=null,
    val bio: String?=null,
    val emailVerify: Boolean?=false,
    val id: Int = 0,
    val latitude: String ?=null,
    val longitude: String?=null,
    val phone: String ?=null,
    val phoneVerify: Boolean?=false,
    val provider_name: String?=null,
    val state: String?=null,
    val updated_at: String ?=null,
    val user_id: Int = 0,
    val user_status: Int = 0,
    val verification_docs: MutableList<String>? = null,
    val verificationDocs: MutableList<Uri>? = null,
    val website_url: String ?=null,
    val zip_code: String?=null
)


/*
data class Data(
    val address: String,
    val available_days: MutableList<String>?=null,
    val available_time: String= "",
    val business_logo: String= "",
    val business_name: String= "",
    val category: MutableList<String>?=null,
    val city: String= "",
    val created_at: String= "",
    val email: String= "",
    val id: Int,
    val latitude: String= "",
    val longitude: String= "",
    val phone: String= "",
    val provider_name: String = "",
    val state: String = "",
    val updated_at: String = "",
    val user_id: Int,
    val user_status: Int,
    val verification_docs: MutableList<String>?=null,
    val website_url: String = "",
    val zip_code: String = ""
)*/
