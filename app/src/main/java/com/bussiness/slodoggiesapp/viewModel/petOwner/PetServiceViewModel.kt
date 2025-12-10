package com.bussiness.slodoggiesapp.viewModel.petOwner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.ownerService.CategoryResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetServiceUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetServicesViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetServiceUiState())
    val uiState: StateFlow<PetServiceUiState> = _uiState

    private val searchQuery = MutableStateFlow("")

    // Original list
    private var allServices: List<ServiceItem> = emptyList()

    init {
        serviceCategoryList()
        observeSearchChanges()
        getPetServices("")
    }

    fun onSearchTextChange(newText: String) {
        _uiState.update { it.copy(searchText = newText) }
        searchQuery.value = newText

        applyFilters()
    }

    fun onServiceTypeSelected(serviceType: String?) {
        _uiState.update { it.copy(selectedServiceType = serviceType) }
        applyFilters()
    }

    /** Debounce search input */
    @OptIn(FlowPreview::class)
    private fun observeSearchChanges() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    getPetServices(query)
                }
        }
    }

    /** Local filtering */
    private fun applyFilters() {
        val state = _uiState.value
        val search = state.searchText.trim()

        val filtered = allServices.filter { service ->
            val serviceName = service.serviceName.orEmpty()
            val categories = service.categoryName ?: emptyList()

            val matchesSearch =
                search.isEmpty() ||
                        serviceName.contains(search, ignoreCase = true)

            val matchesType =
                state.selectedServiceType.isNullOrEmpty() ||
                        categories.any { it.equals(state.selectedServiceType, ignoreCase = true) }

            matchesSearch && matchesType
        }

        _uiState.update { it.copy(services = filtered) }
    }

    /** API call with debounced search */
    private fun getPetServices(query: String) {
        viewModelScope.launch {
            repository.ownerService(sessionManager.getUserId(), query)
                .collectLatest { result ->

                    when (result) {

                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true, error = null) }
                        }

                        is Resource.Success -> {
                            val list = result.data.data ?: emptyList()

                            // update master list
                            allServices = list

                            // Apply filters AFTER load
                            applyFilters()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message ?: "Something went wrong"
                                )
                            }
                        }

                        Resource.Idle -> Unit
                    }
                }
        }
    }

    private fun serviceCategoryList() {
        viewModelScope.launch {
            repository.ownerCategoryService(sessionManager.getUserId())
                .collectLatest { result ->

                    when (result) {

                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true, error = null) }
                        }

                        is Resource.Success -> {
                            val response = result.data as? CategoryResponse
                            val list = response?.data.orEmpty()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    serviceCategory = list,
                                    error = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message ?: "Something went wrong"
                                )
                            }
                        }

                        Resource.Idle -> Unit
                    }
                }
        }
    }
}




