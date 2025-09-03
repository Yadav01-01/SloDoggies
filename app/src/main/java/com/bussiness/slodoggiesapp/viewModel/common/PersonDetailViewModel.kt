package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class PersonDetailUiState(
    val name: String = "Jimmi",
    val breed: String = "Golden Retriever" ?: "Breed",
    val age: String = "6 Years Old" ?: "Age",
    val bio: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed." ?: "Bio",
    val profileImages: List<Int> = listOf(
        R.drawable.sample_user,
        R.drawable.sample_user,
        R.drawable.sample_user,
        R.drawable.sample_user
    ),
    val selectedImageIndex: Int = 0,
    val posts: String = "20",
    val followers: String = "27k",
    val following: String = "219",
    val petOwners: List<PetOwnerDetailUi> = listOf(
        PetOwnerDetailUi("Makenna Bator", "Pet Mom", R.drawable.sample_user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed.")
    ),
    val gallery: List<GalleryItem> = listOf(
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2, isVideo = true),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2)
    )
)

data class PetOwnerDetailUi(
    val name: String,
    val label: String,
    val imageRes: Int,
    val description: String
)

class PersonDetailViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(PersonDetailUiState())
    val uiState: StateFlow<PersonDetailUiState> = _uiState

    fun selectProfileImage(index: Int) {
        _uiState.update { it.copy(selectedImageIndex = index) }
    }

    fun follow() {
        // TODO: integrate API for follow/unfollow
        println("Follow clicked for ${_uiState.value.name}")
    }

    fun message() {
        // TODO: integrate chat API
        println("Message clicked for ${_uiState.value.name}")
    }
}
