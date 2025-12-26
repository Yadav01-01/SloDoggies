package com.bussiness.slodoggiesapp.viewModel.subscriptionViewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.SubscriptionUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState

    init {
        getSubscription()
    }

    private fun getSubscription(){
        viewModelScope.launch {
            repository.getSubscription(sessionManager.getUserId()).collectLatest { subscriptionData ->
                when(subscriptionData){
                    is Resource.Loading -> {
                        _uiState.value = SubscriptionUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = SubscriptionUiState(plans = subscriptionData.data.data, isLoading = false)
                    }
                    is Resource.Error -> {
                        _uiState.value = SubscriptionUiState(errorMessage = subscriptionData.message ?: "Something went wrong", isLoading = false)
                    }
                    is Resource.Idle -> Unit
                }
            }
        }
    }

    fun onPlanSelected(planId: Int) {
        _uiState.update {
            it.copy(selectedPlanId = planId)
        }
    }

    fun onPlanActivated(planId: Int) {
        _uiState.update {
            it.copy(
                activatedPlanId = planId,
                selectedPlanId = planId
            )
        }
    }


    fun onPlanCancelled(planId: Int) {
        _uiState.update {
            if (it.activatedPlanId == planId) {
                it.copy(activatedPlanId = null)
            } else it
        }
    }
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }


}

