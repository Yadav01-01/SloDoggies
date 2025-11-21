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
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.newModel.updatepet.UpdatePetModel
import com.bussiness.slodoggiesapp.network.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface Repository {

    suspend fun sendOtp(emailOrPhone: String,
                        apiType: String
    ): Flow<Resource<OtpResponse>>

    suspend fun registerUser(fullName: String,
                             emailOrPhone: String,
                             password: String,
                             deviceType: String,
                             fcm_token: String,
                             userType: String,
                             otp: String
    ): Flow<Resource<RegisterResponse>>

    suspend fun userLogin( emailOrPhone: String,
                           password: String,
                           deviceType: String,
                           fcm_token: String,
                           userType: String
    ): Flow<Resource<LoginResponse>>


    suspend fun updatePetRequest(petName: String,
                                 petBreed: String,
                                 petAge: String,
                                 petBio: String,
                                 petId: String,
                                 userId: String,
                                 image: MultipartBody.Part?,
    ): Flow<Resource<UpdatePetModel>>

    suspend fun createPostOwnerRequest(writePost: String,
                                 hashTage: String,
                                 location: String,
                                 latitude: String,
                                 longitude: String,
                                 userId: String,
                                 petId: String,
                                 userType: String,
                                 image: List<MultipartBody.Part>?): Flow<Resource<CommonResponse>>

    suspend fun createEventOwnerRequest(userId: String,
                                        postTitle: String,
                                        eventDescription: String,
                                        eventStartDate: String,
                                        eventStartTime: String,
                                        eventEndDate: String,
                                        eventEndTime: String,
                                        address: String,
                                        latitude: String,
                                        longitude: String,
                                        city: String,
                                        state: String,
                                        zipCode: String,
                                        userType: String,
                                        image: List<MultipartBody.Part>?): Flow<Resource<CommonResponse>>



    suspend fun petListRequest(userId: String
    ): Flow<Resource<PetListModel>>

    suspend fun termsConditionRequest(): Flow<Resource<TermsConditionModel>>

    suspend fun logOutRequest(userId: String): Flow<Resource<CommonResponse>>

    suspend fun helpSupportRequest(): Flow<Resource<TermsConditionModel>>

    suspend fun aboutUsRequest(): Flow<Resource<TermsConditionModel>>

    suspend fun privacyPolicyRequest(): Flow<Resource<TermsConditionModel>>

    suspend fun verifyForgotOtp(emailOrPhone: String, otp: String): Flow<Resource<CommonResponse>>

    suspend fun resetPassword(emailOrPhone: String, password: String, ): Flow<Resource<CommonResponse>>

    suspend fun resendOTP(emailOrPhone: String,apiType: String): Flow<Resource<OtpResponse>>

    suspend fun addPets(userId : String,
                        petName : String,
                        petBreed : String,
                        petAge : String,
                        petBio : String,
                        pet_image : List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>>

    suspend fun ownerDetail(userId: String) : Flow<Resource<OwnerDetailsResponse>>

    suspend fun updateOwnerDetail(
        userId: String,
        name: String,
        email: String,
        phone: String,
        bio: String,
        profile_image: List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>>

    suspend fun getBusinessDetail(
        userId: String,
    ) : Flow<Resource<OwnerDetailsResponse>>

    suspend fun registerAndUpdateBusiness(
        userId: String,
        name: String,
        email: String,
        contactNumber : String,
        businessLogo : List<MultipartBody.Part>,
        businessAddress : String,
        businessCategory : String,
        city : String,
        state : String,
        zipCode : String,
        latitude : String,
        longitude : String,
        websiteUrl : String,
        availableDays : String,
        availableTime : String,
        verification_docs : List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>>

    suspend fun addAndUpdateServices(userId: String,
                                     serviceTitle : String,
                                     description :  String,
                                     images :List<MultipartBody.Part>,
                                     price : String,
                                     serviceId : String
    ) : Flow<Resource<CommonResponse>>


    suspend fun getFollowerList(
        userId: String,
        page : String,
        limit : String
    ) : Flow<Resource<FollowersResponse>>

    suspend fun getFollowingList(
        userId: String,
        page : String,
        limit : String
    ) : Flow<Resource<FollowingResponse>>

    suspend fun getBusinessProfileDetail(
        userId: String,
    ) : Flow<Resource<BusinessDetailsResponse>>

    suspend fun getMyPostDetails(
        userId: String,
    ) : Flow<Resource<MyPostsResponse>>


}