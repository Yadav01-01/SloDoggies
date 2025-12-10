package com.bussiness.slodoggiesapp.data.remote

import com.bussiness.slodoggiesapp.data.newModel.BusinessDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.commonresponse.CommonResponse
import com.bussiness.slodoggiesapp.data.newModel.FollowersResponse
import com.bussiness.slodoggiesapp.data.newModel.FollowingResponse
import com.bussiness.slodoggiesapp.data.newModel.LoginResponse
import com.bussiness.slodoggiesapp.data.newModel.MyPostsResponse
import com.bussiness.slodoggiesapp.data.newModel.OtpResponse
import com.bussiness.slodoggiesapp.data.newModel.OwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.RegisterResponse
import com.bussiness.slodoggiesapp.data.newModel.discover.TrendingHashtagsResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.PetOwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.businessdetails.BusinessDetailsModel
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.BusinessProfileModel
import com.bussiness.slodoggiesapp.data.newModel.discover.PetsResponse
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.EventModel
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentReplyResponse
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentResponse
import com.bussiness.slodoggiesapp.data.newModel.home.CommentsResponse
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedResponse
import com.bussiness.slodoggiesapp.data.newModel.otpsendverify.OtpVerifyModel
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerGalleryResponse
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.servicelist.ServicesListModel
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.newModel.updatepet.UpdatePetModel
import com.bussiness.slodoggiesapp.network.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface Repository {

    suspend fun sendOtp(emailOrPhone: String,
                        apiType: String
    ): Flow<Resource<OtpResponse>>

    suspend fun sendOtpRequest(emailOrPhone: String,
                        userId: String
    ): Flow<Resource<OtpVerifyModel>>

    suspend fun otpVerifyEmailPhoneRequest(emailOrPhone: String,
                               userId: String,
                               otp: String
    ): Flow<Resource<OtpVerifyModel>>

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


    suspend fun updateRegistrationRequest(userId: String,
                                          businessName: String,
                                          providerName: String,
                                          email: String,
                                          businessLogo: MultipartBody.Part?,
                                          businessCategory: String,
                                          businessAddress: String,
                                          latitude: String,
                                          longitude: String,
                                          city: String,
                                          state: String,
                                          zipCode: String,
                                          websiteUrl: String,
                                          contactNumber: String,
                                          availableDays: String,
                                          availableTime: String,
                                          bio: String,
                                          imageDoc: List<MultipartBody.Part>?): Flow<Resource<CommonResponse>>




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
        parent_type: String,
        profile_image: List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>>

    suspend fun getBusinessDetail(
        userId: String,
    ) : Flow<Resource<OwnerDetailsResponse>>


    suspend fun getBusinessProfile(
        userId: String,
    ) : Flow<Resource<BusinessProfileModel>>

    suspend fun getBusinessDashboard(
        userId: String,
    ) : Flow<Resource<BusinessDetailsModel>>

    suspend fun servicesListRequest(
        userId: String,
    ) : Flow<Resource<ServicesListModel>>


    suspend fun eventListRequest(
        userId: String,
        page: String,
        type: String
    ) : Flow<Resource<EventModel>>


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
                                     images :List<MultipartBody.Part>?,
                                     price : String,
                                     serviceId : String
    ) : Flow<Resource<CommonResponse>>


    suspend fun getFollowerList(
        userId: String,
        page : String,
        limit : String,
        search : String
    ) : Flow<Resource<FollowersResponse>>

    suspend fun getFollowingList(
        userId: String,
        page : String,
        limit : String,
        search : String
    ) : Flow<Resource<FollowingResponse>>

    suspend fun getBusinessProfileDetail(
        userId: String,
    ) : Flow<Resource<BusinessDetailsResponse>>

    suspend fun getMyPostDetails(
        userId: String,
    ) : Flow<Resource<MyPostsResponse>>

    suspend fun addAndRemoveFollowers(
        userId: String,
        followerId: String,
    ) : Flow<Resource<CommonResponse>>

    suspend fun getOwnerProfileDetails(
        userId: String
    ) : Flow<Resource<PetOwnerDetailsResponse>>

    suspend fun getOwnerGalleryPost(
        userId: String,
        page : String
    ) : Flow<Resource<OwnerGalleryResponse>>

    suspend fun getPetDetail(
        petId: String
    ) : Flow<Resource<UpdatePetModel>>


    suspend fun createAd(userId: String,
                         adTitle: String,
                         adDescription: String,
                         category: String,
                         service: String,
                         expiry_date: String,
                         expiry_time: String,
                         termAndConditions: String,
                         latitude: String,
                         longitude: String,
                         serviceLocation: String,
                         contactNumber: String,
                         budget: String,
                         mobile_visual: String,
                         image: List<MultipartBody.Part>?): Flow<Resource<CommonResponse>>

    suspend fun getHomeFeed(
        userId: String,
        page: String
    ) : Flow<Resource<HomeFeedResponse>>


    suspend fun savePost(
        userId: String,
        postId: String
    ) : Flow<Resource<CommonResponse>>

    suspend fun reportPost(
        userId: String,
        postId: String,
        reportReason: String,
        message: String
    ) : Flow<Resource<CommonResponse>>

    suspend fun postLikeUnlike(
        userId: String,
        postId: String
    ) : Flow<Resource<CommonResponse>>

    suspend fun getComments(
        userId: String,
        postId: String,
        page: String,
        limit: String
    ) : Flow<Resource<CommentsResponse>>

    suspend fun addNewComment(
        userId: String,
        postId: String,
        commentText: String
    ) : Flow<Resource<AddCommentResponse>>


    suspend fun replyComment(
        userId: String,
        postId: String,
        commentId: String,
        commentText: String
    ) : Flow<Resource<AddCommentReplyResponse>>

    suspend fun deleteComment(
        userId: String,
        commentId: String
    ) : Flow<Resource<CommonResponse>>


    suspend fun editComment(
        userId: String,
        commentId: String,
        commenText:String
    ) : Flow<Resource<CommonResponse>>

    suspend fun trendingHashtags() : Flow<Resource<TrendingHashtagsResponse>>

    suspend fun petNearMe(
        userId: String,
        lat: String,
        long: String,
        page: String,
        limit: String,
        search: String
    ) : Flow<Resource<PetsResponse>>

    suspend fun discoverActivities(
        id: String,
        page: String,
        limit: String,
        search: String,
    ) : Flow<Resource<HomeFeedResponse>>

    suspend fun discoverEvents(
        id: String,
        search: String,
        userType: String,
        page: String,
        limit: String,
    ) : Flow<Resource<HomeFeedResponse>>

}