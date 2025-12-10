package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.model.businessProvider.SearchResult
import com.bussiness.slodoggiesapp.data.newModel.discover.HashtagItem
import com.bussiness.slodoggiesapp.data.newModel.discover.PetItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem

data class DiscoverUiState(
    val query: String = "",
    val selectedCategory: String = "Pets Near You",
    val showPetPlaceDialog: Boolean = false,
    val showShareContentDialog: Boolean = false,
    val showReportDialog: Boolean = false,
    val showSavedDialog: Boolean = false,
    val showReportToast: Boolean = false,
    val searchResults: List<SearchResult> = emptyList(),
    val pets: List<PetItem> = emptyList(),
    val posts: List<PostItem> = emptyList(),
    val isLoading: Boolean = false,
    val categories: List<String> = listOf(
        "Pets Near You", "Events", "Pet Places", "Activities"
    ),
    val hashtags: List<HashtagItem> = emptyList(),
    val error: String? = null,
    val page: Int = 1,
    val isLastPage: Boolean = false,
    val isLoadingMore: Boolean = false,
)

