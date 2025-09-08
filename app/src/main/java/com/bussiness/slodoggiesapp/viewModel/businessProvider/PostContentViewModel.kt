package com.bussiness.slodoggiesapp.viewModel.businessProvider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PostContentViewModel @Inject constructor() : ViewModel() {

    private val _writePost = MutableStateFlow("")
    val writePost: StateFlow<String> = _writePost

    private val _hashtags = MutableStateFlow("")
    val hashtags: StateFlow<String> = _hashtags

    private val _postalCode = MutableStateFlow("")
    val postalCode: StateFlow<String> = _postalCode

    private val _visibility = MutableStateFlow("Public")
    val visibility: StateFlow<String> = _visibility

    fun updateWritePost(newWritePost: String) {
        _writePost.value = newWritePost.take(500)
    }

    fun updateHashtags(newHashtags: String) {
        _hashtags.value = newHashtags
    }

    fun updatePostalCode(newPostalCode: String) {
        _postalCode.value = newPostalCode
    }

    fun updateVisibility(newVisibility: String) {
        _visibility.value = newVisibility
    }

    //eventScreen

    private val _eventTitle = MutableStateFlow("")
    val eventTitle: StateFlow<String> = _eventTitle

    private val _eventDescription = MutableStateFlow("")
    val eventDescription: StateFlow<String> = _eventDescription

    private val _eventPostalCode = MutableStateFlow("")
    val eventPostalCode: StateFlow<String> = _eventPostalCode

    fun updateEventTitle(newEventTitle: String) {
        _eventTitle.value = newEventTitle
    }

    fun updateEventDescription(newEventDescription: String) {
        _eventDescription.value = newEventDescription.take(500)
    }

    fun updateEventPostalCode(newEventPostalCode: String) {
        _eventPostalCode.value = newEventPostalCode
    }

    //promotion Screen

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _adDescription = MutableStateFlow("")
    val adDescription: StateFlow<String> = _adDescription

    private val _promoPostalCode = MutableStateFlow("")
    val promoPostalCode: StateFlow<String> = _promoPostalCode

    private val _termsAndConditions = MutableStateFlow("")
    val termsAndConditions: StateFlow<String> = _termsAndConditions

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateAdDescription(newAdDescription: String) {
        _adDescription.value = newAdDescription
    }

    fun updatePromoPostalCode(newPromoPostalCode: String) {
        _promoPostalCode.value = newPromoPostalCode
    }

    fun updateTermsAndConditions(newTermsAndConditions: String) {
        _termsAndConditions.value = newTermsAndConditions
    }

    ///////
    var selectedPet by mutableStateOf<Person?>(null)
        private set

    var allPets by mutableStateOf(listOf<Person>())
        private set

    var isSelecting by mutableStateOf(false)

    fun startSelecting() {
        isSelecting = true
    }

    fun selectPerson(pet: Person) {
        selectedPet = pet
        isSelecting = false
    }

    fun setPets(pets: List<Person>) {
        allPets = pets
    }



}

