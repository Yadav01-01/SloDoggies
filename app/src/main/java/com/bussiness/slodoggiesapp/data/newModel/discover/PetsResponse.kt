package com.bussiness.slodoggiesapp.data.newModel.discover

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class PetsResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: PetsData? = null // Nullable to avoid crashes
) : BaseResponse

data class PetsData(
    val page: Int = 1,
    val limit: Int = 20,
    val total: Int = 0,
    val pets: List<PetItem> = emptyList()
)

data class PetItem(
    val petId: String = "",
    val name: String = "",
    val image: String? = null,
    val pet_owner_id: String? = ""
) {
    // Safe fallback for UI
    val safeImage: String
        get() = image ?: ""  // so Coil doesn't crash

    val hasImage: Boolean
        get() = !image.isNullOrEmpty()
}
