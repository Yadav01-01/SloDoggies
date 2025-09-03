package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _email = MutableStateFlow("rosy@slodoggies.com")
    val email: StateFlow<String> = _email

    private val _description = MutableStateFlow("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed ")
    val description: StateFlow<String> = _description

    private val _providerName = MutableStateFlow("")
    val providerName: StateFlow<String> = _providerName

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun updateProviderName(newProviderName: String) {
        _providerName.value = newProviderName
    }

    fun updateBio(newBio: String) {
        _bio.value = newBio
    }

    fun selectImage(uri: Uri?) {
        _imageUri.value = uri
    }



}