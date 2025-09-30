package com.bussiness.slodoggiesapp.viewModel.petOwner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceContent.PetService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetServicesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PetServiceUiState())
    val uiState: StateFlow<PetServiceUiState> = _uiState

    private val allServices = listOf(
        PetService("Pawfect Pet Care", "Provider Name", "4.8/5", "Grooming"," 1 miles" ,R.drawable.paw_icon),
        PetService("SLO Pet Centre", "Provider Name", "4.8/5", "Walking"," 6 miles", R.drawable.paw_icon),
        PetService("Pawfect Pet Sitters", "Provider Name", "4.8/5", "Boarding"," 8 miles", R.drawable.paw_icon),
        PetService("Dozy Pet Sitters", "Provider Name", "4.8/5", "Boarding"," 7 miles", R.drawable.paw_icon),
        PetService("Grooming Pet Sitters", "Provider Name", "4.8/5", "Grooming"," 2 miles", R.drawable.paw_icon),
        PetService("SLODOG Centre", "Provider Name", "4.8/5", "Veterinary"," 10 miles", R.drawable.paw_icon)
    )

    init {
        _uiState.update { it.copy(services = allServices, filteredServices = allServices) }
    }

    fun onSearchTextChange(newText: String) {
        _uiState.update { it.copy(searchText = newText) }
        filterServices()
    }

    fun onServiceTypeSelected(serviceType: String?) {
        _uiState.update { it.copy(selectedServiceType = serviceType) }
        filterServices()
    }

    private fun filterServices() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val filtered = allServices.filter { service ->
                (currentState.selectedServiceType == null ||
                        (currentState.selectedServiceType == "Search" &&
                                service.name.contains(currentState.searchText, ignoreCase = true)) ||
                        (currentState.selectedServiceType != "Search" &&
                                service.serviceType.equals(currentState.selectedServiceType, ignoreCase = true))) &&
                        (currentState.searchText.isEmpty() ||
                                service.name.contains(currentState.searchText, ignoreCase = true))
            }
            _uiState.update { it.copy(filteredServices = filtered) }
        }
    }
}


data class PetServiceUiState(
    val searchText: String = "",
    val selectedServiceType: String? = null,
    val services: List<PetService> = emptyList(),
    val filteredServices: List<PetService> = emptyList()
)
