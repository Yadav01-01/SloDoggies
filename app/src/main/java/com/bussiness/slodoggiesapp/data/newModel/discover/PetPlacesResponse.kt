package com.bussiness.slodoggiesapp.data.newModel.discover

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class PetPlacesResponse(
    @SerializedName("success") override val success: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") override val message: String = "",
    @SerializedName("data") val data: PetPlacesData = PetPlacesData()
) : BaseResponse

data class PetPlacesData(
    @SerializedName("page") val page: Int = 1,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("total") val total: Int = 0,
    @SerializedName("petPlaces") val petPlaces: List<PetPlaceItem> = emptyList()
)

data class PetPlaceItem(
    @SerializedName("placeId") val placeId: String = "",
    @SerializedName("image") val image: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("overview") val overview: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("distance") val distance: String = "N/A"
)
