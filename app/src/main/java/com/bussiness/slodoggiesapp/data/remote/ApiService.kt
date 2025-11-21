package com.bussiness.slodoggiesapp.data.remote

import com.bussiness.slodoggiesapp.data.newModel.BusinessDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.CommonResponse
import com.bussiness.slodoggiesapp.data.newModel.FollowersResponse
import com.bussiness.slodoggiesapp.data.newModel.FollowingResponse
import com.bussiness.slodoggiesapp.data.newModel.LoginResponse
import com.bussiness.slodoggiesapp.data.newModel.MyPostsResponse
import com.bussiness.slodoggiesapp.data.newModel.OtpResponse
import com.bussiness.slodoggiesapp.data.newModel.OwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {

    @FormUrlEncoded
    @POST(ApiEndPoint.SEND_OTP)
    suspend fun sendOtp(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("apiType") apiType: String
    ): Response<OtpResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.USER_REGISTER)
    suspend fun userRegister(
        @Field("fullName") fullName: String,
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("password") password: String,
        @Field("deviceType") deviceType: String,
        @Field("fcm_token") fcmToken: String,
        @Field("userType") userType: String,
        @Field("otp") otp: String
    ) : Response<RegisterResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.USER_LOGIN)
    suspend fun userLogin(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("password") password: String,
        @Field("deviceType") deviceType: String,
        @Field("fcm_token") fcmToken: String,
    ) : Response<LoginResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_FORGOT_OTP)
    suspend fun verifyForgotOtp(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("otp") otp: String
    ) : Response<CommonResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.UPDATE_BREED)
    suspend fun updatePetRequest(
        @Field("pet_name") petName: String,
        @Field("pet_breed") petBreed: String,
        @Field("pet_age") petAge: String,
        @Field("pet_bio") petBio: String,
        @Field("pet_id") petId: String,
        @Field("user_id") userId: String
       /* @Part petImage:MultipartBody.Part*/
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.RESET_PASSWORD)
    suspend fun resetPassword(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("password") password: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.RESEND_PASSWORD)
    suspend fun resendPassword(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("apiType") apiType: String,
    ) : Response<OtpResponse>

    @Multipart
    @POST(ApiEndPoint.ADD_PETS)
    suspend fun addPets(
        @Part("user_id") userId: RequestBody,
        @Part("pet_name") petName: RequestBody,
        @Part("pet_breed") petBreed: RequestBody,
        @Part("pet_age") petAge: RequestBody,
        @Part("pet_bio") petBio: RequestBody,
        @Part pet_image: List<MultipartBody.Part>
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_OWNER_DETAIL)
    suspend fun getOwnerDetail(
        @Field("user_id") userId: String,
    ) : Response<OwnerDetailsResponse>

    @Multipart
    @POST(ApiEndPoint.UPDATE_OWNER_DETAIL)
    suspend fun updateOwnerDetail(
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part profile_image: List<MultipartBody.Part>
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_BUSINESS_DETAIL)
    suspend fun getBusinessDetail(
        @Field("user_id") userId: String,
    ) : Response<OwnerDetailsResponse>

    @Multipart
    @POST(ApiEndPoint.BUSINESS_REGISTRATION)
    suspend fun registerAndUpdateBusiness(
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("contact_number") contactNumber : RequestBody,
        @Part businessLogo : List<MultipartBody.Part>,
        @Part("business_address") businessAddress : RequestBody,
        @Part("business_category") businessCategory : RequestBody,
        @Part("city") city : RequestBody,
        @Part("state") state : RequestBody,
        @Part("zip_code") zipCode : RequestBody,
        @Part("latitude") latitude : RequestBody,
        @Part("longitude") longitude : RequestBody,
        @Part("website_url") websiteUrl : RequestBody,
        @Part("available_days") availableDays : RequestBody,
        @Part("available_time") availableTime : RequestBody,
        @Part verification_docs : List<MultipartBody.Part>
    ): Response<CommonResponse>

    @Multipart
    @POST(ApiEndPoint.ADD_UPDATE_SERVICE)
    suspend fun addAndUpdateService(
        @Part("user_id") userId: RequestBody,
        @Part("service_title") serviceTitle: RequestBody,
        @Part("description") description: RequestBody,
        @Part images: List<MultipartBody.Part>,
        @Part("price") price : RequestBody,
        @Part("service_id") serviceId : RequestBody,
    ) : Response<CommonResponse>

//    @Multipart
//    @POST(ApiEndPoint.CREATE_OWNER_POST)
//    suspend fun createOwnerPost(
//        @Part("user_id") userId: RequestBody,
//        @Part("pet_id") petId : RequestBody,
//        @Part("post_title") postTitle : RequestBody,
//        @Part("hash_tag") hashTag : RequestBody,
//        @Part("address") address : RequestBody,
//        @Part("city") city : RequestBody,
//        @Part("state") state : RequestBody,
//        @Part("zip_code") zipCode : RequestBody,
//        @Part images: List<MultipartBody.Part>,
//        @Part("user_type") userType : RequestBody,
//        @Part("latitude") latitude : RequestBody,
//        @Part("longitude") longitude : RequestBody,
//    ) :  Response<CommonResponse>

    @Multipart
    @POST(ApiEndPoint.CREATE_OWNER_POST)
    suspend fun createOwnerPost(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part images: List<MultipartBody.Part>
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_FOLLOWER_LIST)
    suspend fun getFollowerList(
        @Field("user_id") userId: String,
    ) : Response<FollowersResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_FOLLOWING_LIST)
    suspend fun getFollowingList(
        @Field("user_id") userId: String,
    ) : Response<FollowingResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_BUSINESS_PROFILE)
    suspend fun getBusinessProfileDetail(
        @Field("user_id") userId: String,
    ) : Response<BusinessDetailsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_MY_POST)
    suspend fun getMyPostDetail(
        @Field("user_id") userId: String,
    ) : Response<MyPostsResponse>

}