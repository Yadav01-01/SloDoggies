package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HelpAndSupportViewModel @Inject constructor() : ViewModel() {

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun updatePhone(value: String) {
        _phone.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }


}