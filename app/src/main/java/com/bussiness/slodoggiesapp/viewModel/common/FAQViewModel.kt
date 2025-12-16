package com.bussiness.slodoggiesapp.viewModel.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.FAQUIState
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FAQViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FAQUIState())
    val uiState: StateFlow<FAQUIState> = _uiState

    // Expand / collapse state (UI-only)
    private val _expandedItems = mutableStateListOf<Int>()
    val expandedItems: List<Int> = _expandedItems

    init {
        loadFAQs()
    }

    private fun loadFAQs() {
        viewModelScope.launch {
            repository.getFAQ().collectLatest { result ->
                when (result) {

                    is Resource.Loading -> {
                        _uiState.value = FAQUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = FAQUIState(
                            data = result.data.data,
                            isLoading = false
                        )
                        _expandedItems.clear()
                    }

                    is Resource.Error -> {
                        _uiState.value = FAQUIState(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    Resource.Idle -> {}
                }
            }
        }
    }

    fun toggleExpanded(index: Int) {
        if (_expandedItems.contains(index)) {
            _expandedItems.remove(index)
        } else {
            _expandedItems.add(index)
        }
    }

    fun isExpanded(index: Int): Boolean {
        return _expandedItems.contains(index)
    }
}
