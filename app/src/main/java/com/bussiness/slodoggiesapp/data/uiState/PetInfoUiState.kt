package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.model.petOwner.PetInfo

data class PetInfoUiState(
    val petName: String = "",
    val petBreed: String = "",
    val petAge: String = "",
    val petBio: String = "",
    val managedBy: String = "Pet Mom",
    val showAgeDropdown: Boolean = false,
    val showManagedByDropdown: Boolean = false
) {
    fun toPetInfo() = PetInfo(
        name = petName,
        breed = petBreed,
        age = petAge,
        bio = petBio,
        managedBy = managedBy
    )
}
