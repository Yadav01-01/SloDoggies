package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

// Main Home Feed Response
data class HomeFeedResponse(
    @SerializedName("success") override val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") override val message: String,
    @SerializedName("data") val data: HomeFeedData
) : BaseResponse

// Home Feed Pagination Data
data class HomeFeedData(
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("total_page") val totalPage: Int,
    @SerializedName("data") val posts: List<PostItem>
)

// Sealed class for different post types
sealed class PostItem {

    data class NormalPost(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("pet_id") val petId: Int? = null,
        @SerializedName("post_title") val postTitle: String = "",
        @SerializedName("address") val address: String? = null,
        @SerializedName("latitude") val latitude: String? = null,
        @SerializedName("longitude") val longitude: String? = null,
        @SerializedName("city") val city: String? = null,
        @SerializedName("state") val state: String? = null,
        @SerializedName("zip_code") val zipCode: String? = null,
        @SerializedName("post_type") val postType: String = "",
        @SerializedName("created_at") val createdAt: String = "",
        @SerializedName("updated_at") val updatedAt: String = "",
        @SerializedName("get_post_like_count") val likeCount: Int = 0,
        @SerializedName("get_post_comment_count") val commentCount: Int = 0,
        @SerializedName("get_post_share_count") val shareCount: Int = 0,
        @SerializedName("following") val following: Boolean = false,
        @SerializedName("user_post") val userPost: Boolean = false,
        @SerializedName("saved") val saved: Boolean = false,
        @SerializedName("time") val time: String = "",
        @SerializedName("hashtags") val hashtags: List<String> = emptyList(),
        @SerializedName("get_user_detail") val userDetail: UserDetail? = null,
        @SerializedName("get_pet_detail") val petDetail: PetDetail? = null,
        @SerializedName("get_post_media") val postMedia: List<MediaItem> = emptyList()
    ) : PostItem()

    data class CommunityPost(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("event_title") val eventTitle: String = "",
        @SerializedName("event_description") val eventDescription: String = "",
        @SerializedName("event_start_date") val eventStartDate: String = "",
        @SerializedName("event_start_time") val eventStartTime: String = "",
        @SerializedName("event_end_date") val eventEndDate: String = "",
        @SerializedName("event_end_time") val eventEndTime: String = "",
        @SerializedName("event_duration") val eventDuration: String? = null,
        @SerializedName("address") val address: String = "",
        @SerializedName("latitude") val latitude: String = "",
        @SerializedName("longitude") val longitude: String = "",
        @SerializedName("city") val city: String? = null,
        @SerializedName("state") val state: String? = null,
        @SerializedName("zip_code") val zipCode: String? = null,
        @SerializedName("event_type") val eventType: String = "",
        @SerializedName("created_at") val createdAt: String = "",
        @SerializedName("updated_at") val updatedAt: String = "",
        @SerializedName("get_event_like_count") val likeCount: Int = 0,
        @SerializedName("get_shared_event_count") val shareCount: Int = 0,
        @SerializedName("following") val following: Boolean = false,
        @SerializedName("user_event") val userEvent: Boolean = false,
        @SerializedName("saved") val saved: Boolean = false,
        @SerializedName("time") val time: String = "",
        @SerializedName("get_user") val userDetail: UserDetail = UserDetail(),
        @SerializedName("get_event_image") val eventImages: List<MediaItem> = emptyList()
    ) : PostItem()

    data class SponsoredPost(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("add_title") val title: String = "",
        @SerializedName("add_description") val description: String = "",
        @SerializedName("service") val service: String? = null,
        @SerializedName("expiry_date") val expiryDate: String? = null,
        @SerializedName("expiry_time") val expiryTime: String? = null,
        @SerializedName("service_location") val location: String? = null,
        @SerializedName("latitude") val latitude: String? = null,
        @SerializedName("longitude") val longitude: String? = null,
        @SerializedName("contact_number") val contactNumber: String? = null,
        @SerializedName("budget") val budget: String? = null,
        @SerializedName("created_at") val createdAt: String = "",
        @SerializedName("updated_at") val updatedAt: String = "",
        @SerializedName("get_add_like_count") val likeCount: Int = 0,
        @SerializedName("get_add_comment_count") val commentCount: Int = 0,
        @SerializedName("get_shared_add_count") val shareCount: Int = 0,
        @SerializedName("get_user") val userDetail: UserDetail = UserDetail(),
        @SerializedName("get_add_media") val mediaList: List<MediaItem> = emptyList(),
        @SerializedName("saved") val saved: Boolean = false,
        @SerializedName("time") val time: String = ""
    ) : PostItem()
}

// MediaItem represents images/videos
data class MediaItem(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("media_path") val mediaPath: String = ""
)

// User details
data class UserDetail(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("image") val image: String? = null
)

// Pet details
data class PetDetail(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("pet_name") val petName: String = "",
    @SerializedName("pet_image") val petImage: String? = null
)
