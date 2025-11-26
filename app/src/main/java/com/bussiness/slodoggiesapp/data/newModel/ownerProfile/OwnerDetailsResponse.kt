package com.bussiness.slodoggiesapp.data.newModel.ownerProfile

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

data class PetOwnerDetailsResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: OwnerData = OwnerData()
) : BaseResponse

data class OwnerData(
    @SerializedName("pets")
    val pets: List<Pet> = emptyList(),

    @SerializedName("owner")
    val owner: Owner = Owner(),

    @SerializedName("post_count")
    val postCount: Int = 0,

    @SerializedName("follower_count")
    val followerCount: Int = 0,

    @SerializedName("following_count")
    val followingCount: Int = 0
)

data class Pet(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("owner_user_id")
    val ownerUserId: Int? = null,

    @SerializedName("pet_name")
    val petName: String? = null,

    @SerializedName("pet_breed")
    val petBreed: String? = null,

    @SerializedName("pet_age")
    val petAge: String? = null,

    @SerializedName("pet_image")
    val petImage: String? = null,

    @SerializedName("pet_bio")
    val petBio: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null
)


data class Owner(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("user_id")
    val userId: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("phone")
    val phone: String = "",

    @SerializedName("address")
    val address: String = "",

    @SerializedName("latitude")
    val latitude: String = "",

    @SerializedName("longitude")
    val longitude: String = "",

    @SerializedName("image")
    val image: String = "",

    @SerializedName("bio")
    val bio: String = "",

    @SerializedName("parent_type")
    val parentType: String = "",

    @SerializedName("user_status")
    val userStatus: Int = 0,

    @SerializedName("created_at")
    val createdAt: String = "",

    @SerializedName("updated_at")
    val updatedAt: String = ""
)


