package com.bussiness.slodoggiesapp.viewModel.businessProvider

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _petPlaceDialog = MutableStateFlow(false)
    val petPlaceDialog: MutableStateFlow<Boolean> = _petPlaceDialog

    private val _showShareContent = MutableStateFlow(false)
    val showShareContent: StateFlow<Boolean> = _showShareContent

    // In real project -> Replace with Repository call (Flow from API)
    private val _searchResults = MutableStateFlow(
        listOf(
            SearchResult("Justin Bator", "Pet Dad", R.drawable.sample_user),
            SearchResult("Luna Smith", "Pet Groomer", R.drawable.sample_user),
            SearchResult("Buddy John", "Trainer", R.drawable.sample_user)
        )
    )
    val searchResults: StateFlow<List<SearchResult>> = _searchResults

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        // TODO: Debounce + API search
    }

    fun selectCategory(category: String) {
        _category.value = category
    }

    fun showPetPlaceDialog() {
        _petPlaceDialog.value = true
    }

    fun dismissPetPlaceDialog() {
        _petPlaceDialog.value = false
    }

    fun showShareContent() {
        _showShareContent.value = true
    }

    fun dismissShareContent() {
        _showShareContent.value = false
    }
}

data class PetPlaceDialogState(
    val showDialog: Boolean = false
)
