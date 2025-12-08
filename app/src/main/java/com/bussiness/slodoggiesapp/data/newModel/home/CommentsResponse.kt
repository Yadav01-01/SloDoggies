package com.bussiness.slodoggiesapp.data.newModel.home
import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

data class CommentsResponse(

    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    override val message: String? = null,

    @SerializedName("data")
    val data: CommentDataWrapper? = null

) : BaseResponse


data class CommentDataWrapper(

    @SerializedName("page")
    val page: String? = null,

    @SerializedName("limit")
    val limit: String? = null,

    @SerializedName("total_count")
    val totalCount: Int? = null,

    @SerializedName("total_page")
    val totalPage: Int? = null,

    @SerializedName("data")
    val data: List<CommentItem>? = emptyList()
)


data class CommentItem(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("user")
    val user: CommentUser? = null,

    @SerializedName("like_count")
    val likeCount: Int? = null,

    @SerializedName("isLikedByCurrentUser")
    val isLikedByCurrentUser: Boolean? = null,

    @SerializedName("replies")
    val replies: List<CommentReply>? = emptyList()
)


data class CommentUser(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("image")
    val image: String? = null
)

data class CommentReply(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("user")
    val user: CommentUser? = null,

    @SerializedName("like_count")
    val likeCount: Int? = null,

    @SerializedName("isLikedByCurrentUser")
    val isLikedByCurrentUser: Boolean? = null
)

