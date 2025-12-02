package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedMapper
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.HomeUiState
import com.bussiness.slodoggiesapp.navigation.Routes
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
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var currentPage = 1
    private var isLastPage = false
    private var isRequestRunning = false

    init {
        loadFirstPage()
        initWelcomeDialog()
    }

    fun loadFirstPage() {
        currentPage = 1
        isLastPage = false
        fetchPosts(isFirstPage = true)
    }

    fun loadNextPage() {
        if (!isLastPage && !isRequestRunning) {
            fetchPosts(isFirstPage = false)
        }
    }

    private fun fetchPosts(isFirstPage: Boolean) {

        if (isRequestRunning) return
        isRequestRunning = true

        // UI loading state
        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {

            repository.getHomeFeed(
                userId = sessionManager.getUserId(),
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {
                        val response = result.data.data

                        val items = response?.items ?: emptyList()
                        val uiPosts = HomeFeedMapper.map(items)

                        _uiState.update { state ->
                            state.copy(
                                posts = if (isFirstPage) uiPosts
                                else state.posts + uiPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        // Safe total page check
                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }

private fun initWelcomeDialog() {
        val userType = sessionManager.getUserType()

        val title = if (userType == UserType.Owner) {
            "Welcome to SloDoggies!"
        } else "Welcome to the Pack!"

        val description = if (userType == UserType.Owner) {
            "You're officially part of the SloDoggies pack! Let's keep the tail wagging!"
        } else {
            "Your business profile is all set. Start engaging with users, managing leads, and promoting your services!"
        }

        val button = "Get Started"

        _uiState.update {
            it.copy(
                showWelcomeDialog = true,
                welcomeTitle = title,
                welcomeDescription = description,
                welcomeButton = button
            )
        }
    }

    fun showContinueAddPetDialog() {
        _uiState.update {
            it.copy(
                showPetInfoDialog = false,
                showContinueAddPetDialog = true
            )
        }
    }

    fun dismissWelcomeDialog() {
        _uiState.update {
            it.copy(
                showWelcomeDialog = false,
                showPetInfoDialog = sessionManager.getUserType() == UserType.Owner
            )
        }
    }

    // Reason selected
    fun onReasonSelected(reason: String) {
        _uiState.value = _uiState.value.copy(selectedReason = reason)
    }

    // Message text
    fun onMessageChange(text: String) {
        _uiState.value = _uiState.value.copy(message = text)
    }

    fun onWelcomeSubmit() {
        dismissWelcomeDialog()
    }

    fun onUserDetailsSubmit() {
        _uiState.update {
            it.copy(
                showUserDetailsDialog = false,
                showProfileCreatedDialog = true
            )
        }
        sessionManager.clearSignupFlow()
    }

    fun showDeleteDialog(){
        _uiState.update { it.copy(deleteDialog = true) }
    }

    fun dismissDeleteDialog(){
        _uiState.update { it.copy(deleteDialog = false) }
    }

    fun showPetInfoDialog() {
        _uiState.update { it.copy(showPetInfoDialog = true) }
    }

    fun showReportDialog() {
        _uiState.update { it.copy(showReportDialog = true) }
    }

    fun showShareContent() {
        _uiState.update { it.copy(showShareContent = true) }
    }

    fun dismissShareContent() {
        _uiState.update { it.copy(showShareContent = false) }
    }

    fun dismissReportDialog() {
        _uiState.update { it.copy(showReportDialog = false) }
    }

    fun dismissPetInfoDialog() {
        _uiState.update {
            it.copy(
                showPetInfoDialog = false,
                showUserDetailsDialog = true
            )
        }
    }

    fun dismissUserDetailsDialog() {
        _uiState.update { it.copy(showUserDetailsDialog = false) }
        sessionManager.clearSignupFlow()
    }

    fun dismissProfileCreatedDialog() {
        _uiState.update { it.copy(showProfileCreatedDialog = false) }
        sessionManager.clearSignupFlow()
    }

    fun dismissContinueAddPetDialog() {
        _uiState.update {
            it.copy(
                showContinueAddPetDialog = false,
                showUserDetailsDialog = true
            )
        }
    }

    fun showReportToast() {
        _uiState.update {
            it.copy(
                showReportDialog = false,
                showReportToast = true
            )
        }
    }

    fun dismissReportToast() {
        _uiState.update { it.copy(showReportToast = false) }
    }

    fun onVerify(navController: NavHostController, type: String, data: String) {
        navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=$data")
    }

    private val _petInfoDialogCount = MutableStateFlow(0)
    val petInfoDialogCount: StateFlow<Int> = _petInfoDialogCount

    fun incrementPetInfoDialogCount() {
        _uiState.update { it.copy(petInfoDialogCount = it.petInfoDialogCount + 1) }
    }

    fun resetPetInfoDialogCount() {
        _uiState.update { it.copy(petInfoDialogCount = 0) }
    }


}
