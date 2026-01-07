package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class ReviewUser( @SerializedName("userId")
                       val userId: Int = 0,

                       @SerializedName("name")
                       val name: String = "",

                       @SerializedName("profileImage")
                       val profileImage: String = "")
