package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class OtpResponse(

    @SerializedName("success")
    override  val success: Boolean? = null,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    override  val message: String? = null,

    @SerializedName("data")
    val data: OtpData? = null
) : BaseResponse

/**
 * Nested data object containing OTP information.
 */
data class OtpData(

    @SerializedName("otp")
    val otp: Int? = null,

    @SerializedName("emailOrPhone")
    val emailOrPhone: String? = null
)
