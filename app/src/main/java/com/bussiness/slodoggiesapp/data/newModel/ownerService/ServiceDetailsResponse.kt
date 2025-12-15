package com.bussiness.slodoggiesapp.data.newModel.ownerService

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

data class ServiceDetailsResponse(
    @SerializedName("success") override val success: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") override val message: String = "",
    @SerializedName("data") val data: ServiceDetailsData = ServiceDetailsData()
) : BaseResponse

data class ServiceDetailsData(
    @SerializedName("businessName") val businessName: String? = null,
    @SerializedName("provider_name") val providerName: String? = null,
    @SerializedName("profileImage") val profileImage: String? = null,
    @SerializedName("category") val category: List<String> = emptyList(),
    @SerializedName("rating") val rating: Int = 0,
    @SerializedName("milesAway") val milesAway: String = "",
    @SerializedName("businessDescription") val businessDescription: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("verificationstatus") val verificationStatus: Boolean = false,
    @SerializedName("services") val services: List<ServiceItemDetails> = emptyList(),
    @SerializedName("ratingsAndReviews") val ratingsAndReviews: RatingsAndReviews = RatingsAndReviews()
)

data class ServiceItemDetails(
    @SerializedName("serviceTitle") val serviceTitle: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("currency") val currency: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("media") val media: List<ServiceMedia> = emptyList()
)

data class ServiceMedia(
    @SerializedName("postId") val postId: Int = 0,
    @SerializedName("mediaUrl") val mediaUrl: String = "",
    @SerializedName("mediaType") val mediaType: String = ""
)

data class RatingsAndReviews(
    @SerializedName("serviceId") val serviceId: Int = 0,
    @SerializedName("averageRating") val averageRating: Int = 0,
    @SerializedName("totalReviews") val totalReviews: Int = 0,
    @SerializedName("ratingDistribution") val ratingDistribution: RatingDistribution = RatingDistribution(),
    @SerializedName("reviews") val reviews: List<ReviewItem> = emptyList()
)

data class RatingDistribution(
    @SerializedName("5") val star5: Int = 0,
    @SerializedName("4") val star4: Int = 0,
    @SerializedName("3") val star3: Int = 0,
    @SerializedName("2") val star2: Int = 0,
    @SerializedName("1") val star1: Int = 0
)

data class ReviewItem(
    @SerializedName("reviewerName") val reviewerName: String? = null,
    @SerializedName("reviewerImage") val reviewerImage: String? = null,
    @SerializedName("rating") val rating: Int = 0,
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("date") val date: String? = null
)

