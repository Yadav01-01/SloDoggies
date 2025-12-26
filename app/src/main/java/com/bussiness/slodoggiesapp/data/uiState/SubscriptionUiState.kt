package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.subscription.SubscriptionPlan

data class SubscriptionUiState(
    val plans: List<SubscriptionPlan> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedPlanId: Int? = null,
    val activatedPlanId: Int? = null
)
