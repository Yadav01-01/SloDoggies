package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.faq.FaqItem

data class FAQUIState(
    val data: List<FaqItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
