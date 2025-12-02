package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class MyPostsResponse(
    @SerializedName("success")
    override val success: Boolean? = null,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    override val message: String? = null,

    @SerializedName("data")
    val data: PostsData? = null
) : BaseResponse

data class PostsData(
    val page: String,
    val limit: String,
    val total_posts: Int,
    val total_page: Int,
    val data: List<PostItem>
)

data class PostItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user_id")
    val userId: Int? = null,

    @SerializedName("pet_id")
    val petId: Int? = null,

    @SerializedName("post_title")
    val postTitle: String? = null,

    @SerializedName("hash_tag")
    val hashTag: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("state")
    val state: String? = null,

    @SerializedName("zip_code")
    val zipCode: String? = null,

    @SerializedName("post_type")
    val postType: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("deleted_at")
    val deletedAt: String? = null,

    @SerializedName("get_post_like_count")
    val postLikeCount: Int? = null,

    @SerializedName("get_post_comment_count")
    val postCommentCount: Int? = null,

    @SerializedName("get_post_share_count")
    val postShareCount: Int? = null,

    @SerializedName("get_saved_post_count")
    val postSavedCount: Int? = null,

    @SerializedName("itemSuccess")
    val itemSuccess: ItemSuccess? = null,

    @SerializedName("get_user_detail")
    val userDetail: UserDetail? = null,

    @SerializedName("get_pet_detail")
    val petDetail: PetDetail? = null,

    @SerializedName("get_post_media")
    val postMedia: List<PostMedia>? = null
)

data class ItemSuccess(
    @SerializedName("isLiked")
    val isLiked: Boolean? = null,

    @SerializedName("isSaved")
    val isSaved: Boolean? = null
)

data class UserDetail(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("image")
    val image: String? = null
)

data class PetDetail(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("pet_name")
    val petName: String? = null,

    @SerializedName("pet_image")
    val petImage: String? = null
)

data class PostMedia(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("post_id")
    val postId: Int? = null,

    @SerializedName("media_path")
    val mediaPath: String? = null,

    @SerializedName("media_type")
    val mediaType: String? = null
)
