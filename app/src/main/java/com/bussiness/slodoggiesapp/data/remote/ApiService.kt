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
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlacesResponse
import com.bussiness.slodoggiesapp.data.newModel.discover.PetsResponse
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.EventModel
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentReplyResponse
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentResponse
import com.bussiness.slodoggiesapp.data.newModel.home.CommentsResponse
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedResponse
import com.bussiness.slodoggiesapp.data.newModel.otpsendverify.OtpVerifyModel
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerGalleryResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.CategoryResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServicesResponse
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.servicelist.ServicesListModel
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
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST(ApiEndPoint.SEND_OTP)
    suspend fun sendOtp(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("apiType") apiType: String
    ): Response<OtpResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.SEND_EMAIL_PHONE_OTP)
    suspend fun sendOtpRequest(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("user_id") userId: String
    ): Response<OtpVerifyModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_EMAIL_PHONE_OTP)
    suspend fun verifyOtpEmailPhoneRequest(
        @Field("emailOrPhone") emailOrPhone: String,
        @Field("user_id") userId: String,
        @Field("otp") otp: String
    ): Response<OtpVerifyModel>


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


    @Multipart
    @POST(ApiEndPoint.BUSINESS_REGISTRATION)
    suspend fun updateRegistrationRequest(
        @Part("user_id") userId: RequestBody,
        @Part("business_name") businessName: RequestBody,
        @Part("provider_name") providerName: RequestBody,
        @Part("email") email: RequestBody,
        @Part businessLogo:MultipartBody.Part?,
        @Part("business_category") businessCategory: RequestBody,
        @Part("business_address") businessAddress: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("city") city: RequestBody,
        @Part("state") state: RequestBody,
        @Part("zip_code") zipCode: RequestBody,
        @Part("website_url") websiteUrl: RequestBody,
        @Part("contact_number") contactNumber: RequestBody,
        @Part("available_days") availableDays: RequestBody,
        @Part("available_time") availableTime: RequestBody,
        @Part("bio") bioUser: RequestBody,
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
        @Part("parent_type") parent_type: RequestBody,
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
        @Part images: List<MultipartBody.Part>?,
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
    @POST(ApiEndPoint.GET_BUSINESS_PROFILE)
    suspend fun getBusinessProfile(
        @Field("user_id") userId: String,
    ) : Response<BusinessProfileModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_BUSINESS_PROFILE_DASHBOARD)
    suspend fun getBusinessDashboard(
        @Field("user_id") userId: String,
    ) : Response<BusinessDetailsModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.SERVICE_LIST)
    suspend fun servicesListRequest(
        @Field("user_id") userId: String,
    ) : Response<ServicesListModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.EVENT_LIST)
    suspend fun eventListRequest(
        @Field("user_id") userId: String,
        @Field("page") page: String,
        @Field("type") type: String
    ) : Response<EventModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_MY_POST)
    suspend fun getMyPostDetail(
        @Field("user_id") userId: String,
        @Field("page") page: String,
    ) : Response<MyPostsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.ADD_REMOVE_FOLLOWER)
    suspend fun addAndRemoveFollowers(
        @Field("user_id") userId: String,
        @Field("followed_id") followerId: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_OWNER_PROFILE)
    suspend fun getOwnerProfile(
        @Field("user_id") userId: String,
    ) : Response<PetOwnerDetailsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_OWNER_GALLERY)
    suspend fun getOwnerGallery(
        @Field("user_id") userId: String,
        @Field("page") page : String
    ) : Response<OwnerGalleryResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_PET_DETAIL)
    suspend fun getPetDetail(
        @Field("pet_id") petId: String,
    ) : Response<UpdatePetModel>


    @Multipart
    @POST(ApiEndPoint.CREATE_AD)
    suspend fun createAd(
        @Part("user_id") userId: RequestBody,
        @Part("adTitle") adTitle: RequestBody,
        @Part("adDescription") adDescription: RequestBody,
        @Part("category[]") category: RequestBody,
        @Part("service") service: RequestBody,
        @Part("expiry_date") expiry_date: RequestBody,
        @Part("expiry_time") expiry_time: RequestBody,
        @Part("termAndConditions") termAndConditions: RequestBody,
        @Part("lat") latitude: RequestBody,
        @Part("long") longitude: RequestBody,
        @Part("serviceLocation") serviceLocation: RequestBody,
        @Part("contactNumber") contactNumber: RequestBody,
        @Part("budget") budget: RequestBody,
        @Part("mobile_visual") mobile_visual: RequestBody,
        @Part petImage:List<MultipartBody.Part>?
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.HOME_FEED)
    suspend fun homeFeed(
        @Field("user_id") userId: String,
        @Field("page") page: String
    ) : Response<HomeFeedResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.SAVE_POST)
    suspend fun savePost(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("event_id") eventId: String,
        @Field("add_id") addId: String
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.REPOST_POST)
    suspend fun reportPost(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("report_reason") report_reason: String,
        @Field("text") message: String
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_LIKE_UNLIKE)
    suspend fun postLikeUnlike(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("event_id") eventId: String,
        @Field("add_id") addId: String
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_COMMENT)
    suspend fun getComments(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("add_id") addId: String,
        @Field("page") page: String,
        @Field("limit") limit: String
    ) : Response<CommentsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.ADD_NEW_COMMENT)
    suspend fun addNewComment(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("comment_text") comment_text: String,
        @Field("add_id") addId: String,
    ) : Response<AddCommentResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.REPLY_COMMENT)
    suspend fun replyComment(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("comment_id") commentId: String,
        @Field("comment_text") commentText: String,
    ) : Response<AddCommentReplyResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.DELETE_COMMENT)
    suspend fun deleteComment(
        @Field("user_id") userId: String,
        @Field("comment_id") commentId: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.EDIT_COMMENT)
    suspend fun editComment(
        @Field("user_id") userId: String,
        @Field("comment_id") commentId: String,
        @Field("comment_text") commentText: String,
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.COMMENT_LIKE)
    suspend fun commentLike(
        @Field("user_id") userId: String,
        @Field("comment_id") commentId: String
    ) : Response<CommonResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.EDIT_POST)
    suspend fun editPost(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("post_description") postDescription: String
    ) : Response<CommonResponse>


    @GET(ApiEndPoint.TRENDING_HASHTAGS)
    suspend fun trendingHashTags() : Response<TrendingHashtagsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.DISCOVER_PET_NEAR_ME)
    suspend fun discoverPetNearMe(
        @Field("user_id") userId: String,
        @Field("lat") lat: String,
        @Field("long") long: String,
        @Field("page") page: String,
        @Field("limit") limit: String,
        @Field("search") search: String,
    ) : Response<PetsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.DISCOVER_ACTIVITIES)
    suspend fun discoverActivities(
        @Field("id") id: String,
        @Field("page") page: String,
        @Field("limit") limit: String,
        @Field("search") search: String,
    ) : Response<HomeFeedResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.DISCOVER_EVENTS)
    suspend fun discoverEvents(
        @Field("id") id: String,
        @Field("search") search: String,
        @Field("userType") userType: String,
        @Field("page") page: String,
        @Field("limit") limit: String,
    ) : Response<HomeFeedResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.OWNER_SERVICE)
    suspend fun ownerService(
        @Field("user_id") userId: String,
        @Field("search") search: String,
    ) : Response<ServicesResponse>

    @GET(ApiEndPoint.OWNER_CATEGORY_SERVICE)
    suspend fun ownerCategoryService(
        @Query("user_id") userId: String,
    ): Response<CategoryResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.OWNER_SERVICE_DETAIL)
    suspend fun ownerServiceDetail(
        @Field("business_user_id") businessUserId: String,
    ) : Response<ServiceDetailsResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.DISCOVER_PET_PLACES)
    suspend fun discoverPetPlaces(
        @Field("user_id") userId: String,
        @Field("search") search: String,
    ) : Response<PetPlacesResponse>




}