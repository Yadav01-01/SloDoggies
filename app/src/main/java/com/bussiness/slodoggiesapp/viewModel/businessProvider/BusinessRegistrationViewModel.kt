package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BusinessRegistrationViewModel @Inject constructor() : ViewModel() {

    // Form State
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url

    private val _contact = MutableStateFlow("")
    val contact: StateFlow<String> = _contact



    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _formSubmitted = MutableStateFlow(false)
    val formSubmitted: StateFlow<Boolean> = _formSubmitted

    // Form State Updaters
    fun updateName(value: String) {
        _name.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updateLocation(value: String) {
        _location.value = value
    }

    fun updateUrl(value: String) {
        _url.value = value
    }

    fun updateContact(value: String) {
        _contact.value = value
    }



    fun selectCategory(category: String) {
        _category.value = category
    }

    fun selectImage(uri: Uri?) {
        _imageUri.value = uri
    }

    // Validation Logic
    private fun isFormValid(): Boolean {
        return _name.value.isNotBlank() && _category.value != null && _imageUri.value != null
    }

    // Submit Form
    fun submitForm() {
        if (isFormValid()) {
            _formSubmitted.value = true
            // TODO: API Call or next screen logic
        } else {
            _formSubmitted.value = false
        }
    }
}
