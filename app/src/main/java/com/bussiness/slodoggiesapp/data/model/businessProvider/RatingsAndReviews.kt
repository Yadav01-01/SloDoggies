package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class RatingsAndReviews(@SerializedName("serviceId")
                               val serviceId: Int = 0,

                               @SerializedName("averageRating")
                               val averageRating: Int = 0,

                               @SerializedName("totalReviews")
                               val totalReviews: Int = 0,

                               @SerializedName("ratingDistribution")
                               val ratingDistribution: Map<String, Int> = emptyMap(),

                               @SerializedName("reviews")
                               val reviews: List<ReviewService> = emptyList())
