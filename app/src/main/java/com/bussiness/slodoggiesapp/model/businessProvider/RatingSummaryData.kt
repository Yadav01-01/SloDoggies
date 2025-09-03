package com.bussiness.slodoggiesapp.model.businessProvider

data class RatingSummaryData(
    val overallRating: Float,
    val totalReviews: Int,
    val ratingPercentages: Map<Int, Float> // e.g., {5 -> 0.8f, 4 -> 0.6f}
)

