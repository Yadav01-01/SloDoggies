package com.bussiness.slodoggiesapp.viewModel.event
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.EventModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.compose.runtime.State




@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventModel())
    val uiState: StateFlow<EventModel> = _uiState.asStateFlow()

    private var currentPage = 1
    var isLoadingPage = false
        private set

    private val _selectedOption = mutableStateOf("My Events")
    val selectedOption: State<String> = _selectedOption

    fun selectOption(option: String) {
        _selectedOption.value = option
        refreshList()
    }

    init {
        getEventList(currentPage)
    }

    private fun getEventList(page: Int) {
        if (isLoadingPage) return
        isLoadingPage = true
        val type=  if (_selectedOption.value.equals("My Events",true)){
            "my_event"
        }else{
            "saved_event"
        }
        viewModelScope.launch {
            repository.eventListRequest(sessionManager.getUserId(), page.toString(),type)
                .collectLatest { result ->
                    _uiState.update { it.copy(isRefreshing = false, isLoading = false) }
                    when (result) {
                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Resource.Success -> {
                            isLoadingPage = false
                            val response = result.data
                            if (response.success == true && response.data != null) {
                                val newData = response.data.data
                                val currentData = _uiState.value.data?.data ?: mutableListOf()
                                // Prevent duplicates
                                val filteredData = newData.filter { newItem ->
                                    currentData.none { it.id == newItem.id }
                                }
                                val updatedData = (currentData + filteredData).toMutableList()
                                _uiState.value = _uiState.value.copy(
                                    data = response.data.copy(data = updatedData)
                                )
                                currentPage = response.data.page.toInt()
                            } else {
                                onError(response.message ?: "Failed to fetch events")
                            }
                        }
                        is Resource.Error -> {
                            isLoadingPage = false
                            onError(result.message)
                        }
                        Resource.Idle -> Unit
                    }
                }
        }
    }

    fun refreshList() {
        currentPage = 1
        _uiState.update { it.copy(isRefreshing = true, data = null) }
        getEventList(currentPage)
    }

    fun loadNextPage() {
        val totalPage = _uiState.value.data?.total_page?.toInt() ?: 1
        if (currentPage < totalPage && !isLoadingPage) {
            getEventList(currentPage + 1)
        }
    }

    private fun onError(message: String?) {
        _uiState.update { it.copy(error = message ?: "Something went wrong") }
    }

}
