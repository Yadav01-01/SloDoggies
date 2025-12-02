package com.bussiness.slodoggiesapp.data.newModel.ownerProfile

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

data class OwnerGalleryResponse(
    @SerializedName("success")
    override val success: Boolean? = false,

    @SerializedName("code")
    val code: Int? = 0,

    @SerializedName("message")
    override val message: String? = "",

    @SerializedName("data")
    val data: OwnerGalleryData? = OwnerGalleryData()
) : BaseResponse

data class OwnerGalleryData(
    @SerializedName("page")
    val page: Int? = 1,

    @SerializedName("limit")
    val limit: Int? = 20,

    @SerializedName("total_data")
    val totalData: Int? = 0,

    @SerializedName("total_page")
    val totalPage: Int? = 0,

    @SerializedName("data")
    val posts: List<OwnerPostItem>? = emptyList()
)

data class OwnerPostItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user_id")
    val userId: Int? = null,

    @SerializedName("pet_id")
    val petId: Int? = null,

    // Post fields
    @SerializedName("post_title")
    val postTitle: String? = "",

    @SerializedName("post_type")
    val postType: String? = "",

    // Event fields
    @SerializedName("event_title")
    val eventTitle: String? = "",

    @SerializedName("event_description")
    val eventDescription: String? = "",

    @SerializedName("event_start_date")
    val eventStartDate: String? = "",

    @SerializedName("event_start_time")
    val eventStartTime: String? = "",

    @SerializedName("event_end_date")
    val eventEndDate: String? = "",

    @SerializedName("event_end_time")
    val eventEndTime: String? = "",

    @SerializedName("event_duration")
    val eventDuration: String? = "",

    @SerializedName("event_type")
    val eventType: String? = "",

    // Common fields
    @SerializedName("address")
    val address: String? = "",

    @SerializedName("latitude")
    val latitude: String? = "",

    @SerializedName("longitude")
    val longitude: String? = "",

    @SerializedName("city")
    val city: String? = "",

    @SerializedName("state")
    val state: String? = "",

    @SerializedName("zip_code")
    val zipCode: String? = "",

    @SerializedName("created_at")
    val createdAt: String? = "",

    @SerializedName("updated_at")
    val updatedAt: String? = "",

//    @SerializedName("media_path")
//    val mediaPath: List<String>? = emptyList()
    @SerializedName("media")
    val mediaPath: List<MediaItem>? = emptyList()
)

data class MediaItem(
    val url: String?,
    val type: String?
)
