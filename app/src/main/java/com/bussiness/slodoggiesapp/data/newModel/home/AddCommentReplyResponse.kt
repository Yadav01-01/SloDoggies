package com.bussiness.slodoggiesapp.data.newModel.home

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

data class AddCommentReplyResponse(

    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    override val message: String? = null,

    @SerializedName("data")
    val data: AddedCommentReplyData? = null

) : BaseResponse


data class AddedCommentReplyData(

    @SerializedName("user_id")
    val userId: String? = null,

    @SerializedName("post_id")
    val postId: String? = null,

    @SerializedName("parent_comment_id")
    val parentCommentId: String? = null,  // <-- new field for reply

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("id")
    val id: Int? = null
)
