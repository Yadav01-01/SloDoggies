package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class ServiceMedia(   @SerializedName("postId")
                           val postId: Int = 0,

                           @SerializedName("mediaUrl")
                           val mediaUrl: String = "",

                           @SerializedName("mediaType")
                           val mediaType: String = "")
