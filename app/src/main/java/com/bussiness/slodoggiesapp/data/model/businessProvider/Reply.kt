package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class Reply(  @SerializedName("replyId")
                   val replyId: Int = 0,

                   @SerializedName("user")
                   val user: ReviewUser? = null,

                   @SerializedName("timeAgo")
                   val timeAgo: String = "",

                   @SerializedName("comment")
                   val comment: String = "",

                   @SerializedName("createdAt")
                   val createdAt: String = ""
)
