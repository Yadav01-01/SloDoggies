package com.bussiness.slodoggiesapp.viewModel.businessProvider

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }
    
    fun selectCategory(category: String) {
        _category.value = category
    }

}