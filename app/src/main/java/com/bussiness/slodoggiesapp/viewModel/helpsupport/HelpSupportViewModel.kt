package com.bussiness.slodoggiesapp.viewModel.helpsupport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpSupportViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(TermsConditionModel())
    val uiState: StateFlow<TermsConditionModel> = _uiState


    fun helpSupportRequest(onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.helpSupportRequest().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success == true) {
                                _uiState.value = _uiState.value.copy(
                                    data = response.data
                                )
                                onSuccess()
                            } else {
                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }
    }

}



