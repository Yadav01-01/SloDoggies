package com.bussiness.slodoggiesapp.viewModel.privacypolicy

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.petlist.Data
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetAddUpDateUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipart
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.termscondition.TermsAndConditionViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(TermsConditionModel())
    val uiState: StateFlow<TermsConditionModel> = _uiState


    fun privacyPolicyRequest(onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.privacyPolicyRequest().collectLatest { result ->
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

