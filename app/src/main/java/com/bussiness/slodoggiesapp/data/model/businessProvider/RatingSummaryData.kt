package com.bussiness.slodoggiesapp.data.model.businessProvider

data class RatingSummaryData(
    val overallRating: Float,
    val totalReviews: Int,
    val ratingPercentages: Map<Int, Float>, // e.g., {5 -> 0.8f, 4 -> 0.6f}
    val reviewCounts: Map<Int, Int> // e.g., {5 -> 10, 4 -> 5}
)

