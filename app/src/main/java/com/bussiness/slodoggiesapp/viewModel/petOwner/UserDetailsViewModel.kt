package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.UserDetailsUiState
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    // 🔹 Multiple image support
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())
    val selectedPhotos: StateFlow<List<Uri>> = _selectedPhotos



    private var hasFetchedUser = false

    // --- Update field inputs ---
    fun onNameChanged(name: String) = _uiState.update { it.copy(name = name.take(30)) }
    fun onPhoneChanged(phone: String) = _uiState.update { it.copy(phoneNumber = phone.take(10)) }
    fun onBioChanged(bio: String) = _uiState.update { it.copy(bio = bio) }

    fun onEmailChanged(email: String) = _uiState.update { it.copy(email = email) }

    fun setPhoneVerified(verified: Boolean) = _uiState.update { it.copy(isPhoneVerified = verified) }
    fun setEmailVerified(verified: Boolean) = _uiState.update { it.copy(isEmailVerified = verified) }

    // --- Multiple photo handling ---
    fun addPhoto(uri: Uri) {
        _selectedPhotos.update { it + uri }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isEmailVerified = true) }
        }
    }

    // --- API Call: Fetch Owner Details ---
    fun fetchOwnerDetails() {
        if (hasFetchedUser) return
        hasFetchedUser = true

        viewModelScope.launch {
            repository.ownerDetail(sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val response = result.data
                        if (response.success && response.data != null) {
                            val owner = response.data
                            _uiState.update {
                                it.copy(
                                    name = owner.name.orEmpty(),
                                    email = owner.email.orEmpty(),
                                    phoneNumber = owner.phone.orEmpty(),
                                    isEmailVerified = !owner.email.isNullOrBlank(),
                                    isPhoneVerified = !owner.phone.isNullOrBlank(),
                                    isLoading = false
                                )
                            }
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    // --- Validate + Submit Owner Details ---
    fun submitDetails(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val name = state.name.trim()
            val photos = _selectedPhotos.value

            when {
                name.isEmpty() -> {
                    Toast.makeText(context, Messages.NAME_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                photos.isEmpty() -> {
                    Toast.makeText(context, Messages.PROFILE_IMAGE_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                !state.isPhoneVerified -> {
                    Toast.makeText(context, "Please verify your phone number before proceeding.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                !state.isEmailVerified -> {
                    Toast.makeText(context, "Please verify your email address before proceeding.", Toast.LENGTH_SHORT).show()
                    return@launch
                }
            }

            //  Convert all images to multipart parts
            val imageParts = getImagePartsFromUris(context, photos)
            if (imageParts.isEmpty()) {
                Toast.makeText(context, "Unable to process images.", Toast.LENGTH_SHORT).show()
                return@launch
            }

            //  API Call for multi-image update
            repository.updateOwnerDetail(
                sessionManager.getUserId(),
                name,
                state.email,
                state.phoneNumber,
                state.bio,
                "",
                imageParts
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        val response = result.data
                        if (response.success) {
                            Toast.makeText(context, response.message ?: "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            onSuccess()
                        } else {
                            Toast.makeText(context, response.message ?: "Update failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Toast.makeText(context, result.message ?: "An error occurred", Toast.LENGTH_SHORT).show()
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    fun sendOtpRequest(type:String?="",
                       onSuccess: () -> Unit = { },
                       onError: (String) -> Unit){
        viewModelScope.launch {
            val state = _uiState.value
            val email= state.email
            val phone = state.phoneNumber
            var phoneEmail = ""

            if (type.equals("dialogPhone")){
                phoneEmail = phone
                if (phone.isEmpty()){
                    onError("Phone cant be empty")
                    return@launch
                }
            }else{
                phoneEmail =  email
                if (phoneEmail.isEmpty()){
                    onError("Email cant be empty")
                    return@launch
                }
            }
            repository.sendOtpRequest(phoneEmail,sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
                            onSuccess()
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    private fun onError(message: String?) {
        _uiState.update { it.copy(errorMessage = message ?: "Something went wrong") }
    }

    //  Helper for multiple Uris → MultipartBody.Part list
    private fun getImagePartsFromUris(context: Context, uris: List<Uri>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        val contentResolver = context.contentResolver
        uris.forEachIndexed { index, uri ->
            try {
                val inputStream = contentResolver.openInputStream(uri) ?: return@forEachIndexed
                val fileBytes = inputStream.readBytes()
                inputStream.close()

                val fileName = "image_${System.currentTimeMillis()}_${index}.jpg"
                val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
                val part = MultipartBody.Part.createFormData("images[]", fileName, requestBody)
                parts.add(part)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return parts
    }
}
