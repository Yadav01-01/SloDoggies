package com.bussiness.slodoggiesapp.viewModel.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.data.model.common.FAQItem
import javax.inject.Inject

class FAQViewModel @Inject constructor() : ViewModel() {
    private val _faqItems = mutableStateListOf<Pair<FAQItem, Boolean>>() // Boolean = isExpanded
    val faqItems: List<Pair<FAQItem, Boolean>> get() = _faqItems

    init {
        loadFAQs() // Load sample data
    }

    private fun loadFAQs() {
        val sampleData = listOf(
            FAQItem(
                1,
                "What is Slodoggies?",
                "Slodoggies is a community-driven app for pet lovers and service providers in San Luis Obispo County."
            ),
            FAQItem(
                2,
                "Who can join Slodoggies?",
                "Anyone who loves pets or provides pet-related services."
            ),
            FAQItem(
                3,
                "How do I create a profile?",
                "Download the app and follow the registration steps."
            ),
            FAQItem(
                4,
                "Is there a cost to use the app?",
                "No, it's free for all users."
            ),
            FAQItem(
                5,
                "Can I create events or meetups?",
                "Yes, pet owners and businesses can host events."
            ),
            FAQItem(
                6,
                "How do I promote my pet business?",
                "Create a business profile and list your services."
            ),
            FAQItem(
                7,
                "How is my information used?",
                "Your data is kept secure and used to improve your experience."
            ),
            FAQItem(
                8,
                "How do I contact support?",
                "Use the contact form or email us at help@slodoggies.com"
            )
        )
        _faqItems.clear()
        _faqItems.addAll(sampleData.map { it to false }) // default not expanded
    }

    fun toggleExpanded(index: Int) {
        _faqItems[index] = _faqItems[index].copy(second = !_faqItems[index].second)
    }

}