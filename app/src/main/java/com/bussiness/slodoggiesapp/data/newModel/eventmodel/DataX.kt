package com.bussiness.slodoggiesapp.data.newModel.eventmodel

data class DataX(
    val address: String?=null,
    val city: String?=null,
    val created_at: String?=null,
    val event_description: String?=null,
    val event_duration: Any,
    val event_end_date: String?=null,
    val event_end_time: String?=null,
    val event_start_date: String?=null,
    val event_start_time: String?=null,
    val event_title: String?=null,
    val event_type: String?=null,
    val get_event_image: MutableList<GetEventImage>?=null,
    val id: Int=0,
    val latitude: String?=null,
    val longitude: String?=null,
    val state: String?=null,
    val updated_at: String?=null,
    val user_id: Int,
    val zip_code: String?=null
)