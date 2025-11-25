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
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.PetOwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.newModel.updatepet.UpdatePetModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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
        @Field("userType") userType: String
    ) : Response<LoginResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_FORGOT_OTP)
    suspend fun verifyForgotOtp(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("otp") otp: String
    ) : Response<CommonResponse>


    @GET(ApiEndPoint.TERMS_CONDITION)
    suspend fun termsConditionRequest() : Response<TermsConditionModel>

    @GET(ApiEndPoint.HELP_SUPPORT)
    suspend fun helpSupportRequest() : Response<TermsConditionModel>

    @GET(ApiEndPoint.PRIVACY_POLICY)
    suspend fun privacyPolicyRequest() : Response<TermsConditionModel>

    @GET(ApiEndPoint.ABOUT_US)
    suspend fun aboutUsRequest() : Response<TermsConditionModel>


    @Multipart
    @POST(ApiEndPoint.UPDATE_BREED)
    suspend fun updatePetRequest(
        @Part("pet_name") petName: RequestBody,
        @Part("pet_breed") petBreed: RequestBody,
        @Part("pet_age") petAge: RequestBody,
        @Part("pet_bio") petBio: RequestBody,
        @Part("pet_id") petId: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part petImage:MultipartBody.Part?
    ) : Response<UpdatePetModel>


    @Multipart
    @POST(ApiEndPoint.CREATE_POST_OWNER)
    suspend fun createPostOwnerRequest(
        @Part("user_id") userId: RequestBody,
        @Part("post_title") postTitle: RequestBody,
        @Part("hash_tag") hashTag: RequestBody,
        @Part("address") petBio: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("pet_id") petId: RequestBody,
        @Part("userType") userType: RequestBody,
        @Part petImage:List<MultipartBody.Part>?
    ) : Response<CommonResponse>

    @Multipart
    @POST(ApiEndPoint.CREATE_EVENT_OWNER)
    suspend fun createEventOwnerRequest(
        @Part("user_id") userId: RequestBody,
        @Part("event_title") postTitle: RequestBody,
        @Part("event_description") eventDescription: RequestBody,
        @Part("event_start_date") eventStartDate: RequestBody,
        @Part("event_start_time") eventStartTime: RequestBody,
        @Part("event_end_date") eventEndDate: RequestBody,
        @Part("event_end_time") eventEndTime: RequestBody,
        @Part("address") petBio: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("city") city: RequestBody,
        @Part("state") state: RequestBody,
        @Part("zip_code") zipCode: RequestBody,
        @Part("userType") userType: RequestBody,
        @Part petImage:List<MultipartBody.Part>?
    ) : Response<CommonResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.RESET_PASSWORD)
    suspend fun resetPassword(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("password") password: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.PET_LIST)
    suspend fun petListRequest(@Field("user_id") userId: String) : Response<PetListModel>

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

    @FormUrlEncoded
    @POST(ApiEndPoint.LOG_OUT)
    suspend fun logOutRequest( @Field("user_id") userId: String,) : Response<CommonResponse>

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


    @FormUrlEncoded
    @POST(ApiEndPoint.GET_FOLLOWER_LIST)
    suspend fun getFollowerList(
        @Field("user_id") userId: String,
        @Field("page") page : String,
        @Field("limit") limit : String,
        @Field("search") search : String,
    ) : Response<FollowersResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_FOLLOWING_LIST)
    suspend fun getFollowingList(
        @Field("user_id") userId: String,
        @Field("page") page : String,
        @Field("limit") limit : String,
        @Field("search") search : String,
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

    @FormUrlEncoded
    @POST(ApiEndPoint.ADD_REMOVE_FOLLOWER)
    suspend fun addAndRemoveFollowers(
        @Field("user_id") userId: String,
        @Field("followed_id") followerId: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_OWNER_GALLERY)
    suspend fun getOwnerProfile(
        @Field("user_id") userId: String,
    ) : Response<PetOwnerDetailsResponse>

}