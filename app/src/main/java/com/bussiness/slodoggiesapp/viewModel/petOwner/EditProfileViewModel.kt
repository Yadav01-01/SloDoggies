package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

data class EditProfileUiState(
    val name: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val bio: String = "",
    val relation: String = "",
    val showRelationDropdown: Boolean = false,
    var profileImageUri: Uri? = null,
    val showImagePickerDialog: Boolean = false,
    val isMobileVerified: Boolean = false,
    val isEmailVerified: Boolean = false,
    val updateProfileDialog: Boolean = false,
    val relationOptions: List<String> = listOf("Father", "Mother", "Partner", "Etc")
)

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState

    // --- Update Fields ---
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateBio(bio: String) {
        _uiState.value = _uiState.value.copy(bio = bio)
    }

    fun updateRelation(relation: String) {
        _uiState.value = _uiState.value.copy(relation = relation, showRelationDropdown = false)
    }

    fun updateProfileImage(uri: Uri) {
        _uiState.value = _uiState.value.copy(profileImageUri = uri)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updateMobileNumber(mobileNumber: String) {
        _uiState.value = _uiState.value.copy(mobileNumber = mobileNumber.take(10))
    }

    // --- Toggles ---
    fun toggleRelationDropdown() {
        _uiState.value = _uiState.value.copy(
            showRelationDropdown = !_uiState.value.showRelationDropdown
        )
    }

    fun toggleImagePickerDialog() {
        _uiState.value = _uiState.value.copy(
            showImagePickerDialog = !_uiState.value.showImagePickerDialog
        )
    }

    fun hideImagePickerDialog() {
        _uiState.value = _uiState.value.copy(showImagePickerDialog = false)
    }

    fun toggleUpdateProfileDialog() {
        _uiState.value = _uiState.value.copy(
            updateProfileDialog = !_uiState.value.updateProfileDialog
        )
    }

    fun hideUpdateProfileDialog(navController: NavController) {
        _uiState.value = _uiState.value.copy(updateProfileDialog = false)
        navController.navigate(Routes.PET_PROFILE_SCREEN)
    }

    // --- Verification Flags ---
    fun setPhoneVerified(verified: Boolean) {
        _uiState.value = _uiState.value.copy(isMobileVerified = verified)
    }

    fun setEmailVerified(verified: Boolean) {
        _uiState.value = _uiState.value.copy(isEmailVerified = verified)
    }

    fun onVerify(navController: NavHostController, type: String, data: String) {
        navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=${data}")
    }

    companion object {
        /** Save Bitmap to Cache and return URI */
        fun Bitmap.saveToCache(context: Context): Uri {
            val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
            FileOutputStream(file).use { out ->
                compress(Bitmap.CompressFormat.JPEG, 92, out)
            }
            return Uri.fromFile(file)
        }
    }
}
