package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class ReviewService( @SerializedName("reviewId")
                          val reviewId: Int = 0,

                          @SerializedName("user")
                          val user: ReviewUser? = null,

                          @SerializedName("rating")
                          val rating: Int = 0,

                          @SerializedName("timeAgo")
                          val timeAgo: String = "",

                          @SerializedName("comment")
                          val comment: String = "",

                          @SerializedName("replies")
                          val replies: List<Reply> = emptyList(),

                          @SerializedName("canReply")
                          val canReply: Boolean = false,

                          @SerializedName("createdAt")
                          val createdAt: String = "")
