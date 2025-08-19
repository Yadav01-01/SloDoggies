package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.model.common.WelcomeUiState
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostItem>>(emptyList())
    val posts: StateFlow<List<PostItem>> = _posts

    private val _welcomeUiState = MutableStateFlow(WelcomeUiState())
    val welcomeUiState: StateFlow<WelcomeUiState> = _welcomeUiState

    private val _showPetInfoDialog = MutableStateFlow(false)
    val showPetInfoDialog: StateFlow<Boolean> = _showPetInfoDialog

    private val _showUserDetailsDialog = MutableStateFlow(false)
    val showUserDetailsDialog: StateFlow<Boolean> = _showUserDetailsDialog

    private val _profileCreatedUiState = MutableStateFlow(false)
    val profileCreatedUiState: StateFlow<Boolean> = _profileCreatedUiState

    init {
        loadPosts()
        checkWelcomeDialog()
    }

    private fun loadPosts() {
        // Later replace with repository call
        _posts.value = emptyList()
    }

    private fun checkWelcomeDialog() {
        val userType = sessionManager.getUserType()
        _welcomeUiState.value = WelcomeUiState(
            showDialog = true,
            title = if (userType == UserType.PET_OWNER) {
                "Welcome to SloDoggies!"
            } else {
                "Welcome to the Pack!"
            },
            description = if (userType == UserType.PET_OWNER) {
                "You're officially part of the SloDoggies pack! Let's keep the tail wagging!"
            } else {
                "Your business profile is all set. Start engaging with users, managing leads, and promoting your services to local dog owners!"
            },
            button = "Get Started"
        )
    }

    fun dismissWelcomeDialog() {
        _welcomeUiState.value = _welcomeUiState.value.copy(showDialog = false)
        if (sessionManager.getUserType() == UserType.PET_OWNER) {
            _showPetInfoDialog.value = true
        }
    }

    fun onWelcomeSubmit() {
        if (sessionManager.getUserType() == UserType.PET_OWNER) {
            _welcomeUiState.value = _welcomeUiState.value.copy(showDialog = false)
            _showPetInfoDialog.value = true
        } else {
            _welcomeUiState.value = _welcomeUiState.value.copy(showDialog = false)
        }
    }

    fun onUserDetailsSubmit() {
        // 2. Close dialog
        _showUserDetailsDialog.value = false
        // 3. Show profile created dialog
        _profileCreatedUiState.value = true
    }


    fun dismissPetInfoDialog() {
        _showPetInfoDialog.value = false
        _showUserDetailsDialog.value = true
    }

    fun dismissUserDetailsDialog() {
        _showUserDetailsDialog.value = false
    }

    fun dismissProfileCreatedDialog() {
        _profileCreatedUiState.value = false
    }
}
