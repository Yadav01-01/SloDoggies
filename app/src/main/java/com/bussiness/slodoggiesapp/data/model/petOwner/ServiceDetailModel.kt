package com.bussiness.slodoggiesapp.data.model.petOwner

data class ServiceDetailModel (
    val providerName: String,
    val rating: Double,
    val totalReviews: Int,
    val serviceType: String,
    val isVerified: Boolean,
    val profileImageUrl: String,
    val additionalInfo: AdditionalInfoItem,
    val services: List<ServiceItem>,
    val ratingReviews: RatingReviewResponse
)

data class AdditionalInfoItem(
    val phoneNumber: String,
    val website: String,
    val address: String,
    val businessDescription: String
)

data class ServiceItem(
    val title: String,
    val description: String?,
    val photos: List<String>,
    val amount: Double?
)

data class RatingReviewResponse(
    val overallRating: Float,
    val totalReviews: Int,
    val ratingDistribution: RatingDistribution,
    val reviews: List<com.bussiness.slodoggiesapp.data.model.petOwner.UserReview>
)

data class RatingDistribution(
    val fiveStar: Int,
    val fourStar: Int,
    val threeStar: Int,
    val twoStar: Int,
    val oneStar: Int
)

data class UserReview(
    val userName: String,
    val userImage: String?,
    val rating: Float,
    val timeAgo: String,
    val comment: String
)
