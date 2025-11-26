package com.bussiness.slodoggiesapp.viewModel.petOwner.petlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.petlist.Data
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetAddUpDateUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipart
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetListViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetListModel())
    val uiState: StateFlow<PetListModel> = _uiState

    init {
        if (sessionManager.getUserType().toString().equals("Owner",true)){
            petListRequest ()
        }

    }

    var selectedPet by mutableStateOf<Data?>(null)
        private set

    fun selectPerson(pet: Data) {
        selectedPet = pet
    }

    fun petListRequest() {
        viewModelScope.launch {
            repository.petListRequest(userId = sessionManager.getUserId()).collectLatest { result ->
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
                            } else {
//                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
//                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }
    }

}



