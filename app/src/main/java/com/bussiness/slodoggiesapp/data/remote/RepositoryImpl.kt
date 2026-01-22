package com.bussiness.slodoggiesapp.data.remote

import android.util.Log
import com.bussiness.slodoggiesapp.data.model.businessProvider.BusinessData
import com.bussiness.slodoggiesapp.data.model.businessProvider.DeleteResponse
import com.bussiness.slodoggiesapp.data.model.common.AllChatListResponse
import com.bussiness.slodoggiesapp.data.model.common.CreateChannelResponse
import com.bussiness.slodoggiesapp.data.model.common.ErrorResponse
import com.bussiness.slodoggiesapp.data.model.common.JoinCommunityRequest
import com.bussiness.slodoggiesapp.data.model.common.JoinCommunityResponse
import com.bussiness.slodoggiesapp.data.model.common.UserImageResponse
import com.bussiness.slodoggiesapp.data.model.petOwner.FollowStatusResponse
import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.bussiness.slodoggiesapp.data.newModel.businessDetail.BusinessDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.commonresponse.CommonResponse
import com.bussiness.slodoggiesapp.data.newModel.followerresponse.FollowersResponse
import com.bussiness.slodoggiesapp.data.newModel.followerresponse.FollowingResponse
import com.bussiness.slodoggiesapp.data.newModel.authresponse.LoginResponse
import com.bussiness.slodoggiesapp.data.newModel.MyPostsResponse
import com.bussiness.slodoggiesapp.data.newModel.authresponse.OtpResponse
import com.bussiness.slodoggiesapp.data.newModel.authresponse.OwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.authresponse.RegisterResponse
import com.bussiness.slodoggiesapp.data.newModel.discover.TrendingHashtagsResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.PetOwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.businessdetails.BusinessDetailsModel
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.BusinessProfileModel
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlacesResponse
import com.bussiness.slodoggiesapp.data.newModel.discover.PetsResponse
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.EventModel
import com.bussiness.slodoggiesapp.data.newModel.faq.FaqResponse
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentReplyResponse
import com.bussiness.slodoggiesapp.data.newModel.home.AddCommentResponse
import com.bussiness.slodoggiesapp.data.newModel.home.CommentsResponse
import com.bussiness.slodoggiesapp.data.newModel.home.FriendListResponse
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedResponse
import com.bussiness.slodoggiesapp.data.newModel.otpsendverify.OtpVerifyModel
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerGalleryResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.CategoryResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServicesResponse
import com.bussiness.slodoggiesapp.data.newModel.petlist.PetListModel
import com.bussiness.slodoggiesapp.data.newModel.servicelist.ServicesListModel
import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdsResponse
import com.bussiness.slodoggiesapp.data.newModel.subscription.SubscriptionResponse
import com.bussiness.slodoggiesapp.data.newModel.termscondition.TermsConditionModel
import com.bussiness.slodoggiesapp.data.newModel.updatepet.UpdatePetModel
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.LoaderManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val api: ApiService) : Repository {

    override suspend fun sendOtp(
        emailOrPhone: String,
        apiType: String
    ): Flow<Resource<OtpResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.sendOtp(emailOrPhone, apiType) })
    }.flowOn(Dispatchers.IO)

    override suspend fun sendOtpRequest(
        emailOrPhone: String,
        userId: String
    ): Flow<Resource<OtpVerifyModel>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.sendOtpRequest(emailOrPhone, userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun otpVerifyEmailPhoneRequest(
        emailOrPhone: String,
        userId: String,
        otp: String
    ): Flow<Resource<OtpVerifyModel>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.verifyOtpEmailPhoneRequest(emailOrPhone, userId,otp) })
    }.flowOn(Dispatchers.IO)

    override suspend fun registerUser(
        fullName: String,
        emailOrPhone: String,
        password: String,
        deviceType: String,
        fcm_token: String,
        userType: String,
        otp: String
    ): Flow<Resource<RegisterResponse>> = flow {
        emit(Resource.Loading)

        emit(safeApiCall {
            api.userRegister(fullName, emailOrPhone, password, deviceType, fcm_token, userType, otp)
           }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun userLogin(
        emailOrPhone: String,
        password: String,
        deviceType: String,
        fcm_token: String,
        userType: String
    ): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.userLogin(emailOrPhone,password, deviceType, fcm_token,userType) })
    }.flowOn(Dispatchers.IO)

    override suspend fun updatePetRequest(
        petName: String,
        petBreed: String,
        petAge: String,
        petBio: String,
        petId: String,
        userId: String,
        image: MultipartBody.Part?
    ): Flow<Resource<UpdatePetModel>> = flow{
        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val name = petName.toRequestBody("text/plain".toMediaTypeOrNull())
        val breed = petBreed.toRequestBody("text/plain".toMediaTypeOrNull())
        val age = petAge.toRequestBody("text/plain".toMediaTypeOrNull())
        val bio = petBio.toRequestBody("text/plain".toMediaTypeOrNull())
        val petUserId = petId.toRequestBody("text/plain".toMediaTypeOrNull())
        emit(Resource.Loading)
        emit(safeApiCall { api.updatePetRequest(name,breed,age,bio,petUserId,id,image) })
    }.flowOn(Dispatchers.IO)

    override suspend fun createPostOwnerRequest(
        writePost: String,
        hashTage: String,
        location: String,
        latitude: String,
        longitude: String,
        userId: String,
        petId: String,
        userType: String,
        image: List<MultipartBody.Part>?
    ): Flow<Resource<CommonResponse>> = flow {
        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val petUserid = petId.toRequestBody("text/plain".toMediaTypeOrNull())
        val post = writePost.toRequestBody("text/plain".toMediaTypeOrNull())
        val tage = hashTage.toRequestBody("text/plain".toMediaTypeOrNull())
        val locationUser = location.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longi = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val type = userType.toRequestBody("text/plain".toMediaTypeOrNull())
        emit(Resource.Loading)
        emit(safeApiCall { api.createPostOwnerRequest(id,post,tage,locationUser,lat,longi,petUserid,type,image) })
    }.flowOn(Dispatchers.IO)

    override suspend fun createEventOwnerRequest(
        userId: String,
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
        image: List<MultipartBody.Part>?
    ): Flow<Resource<CommonResponse>> = flow {
        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val title = postTitle.toRequestBody("text/plain".toMediaTypeOrNull())
        val description = eventDescription.toRequestBody("text/plain".toMediaTypeOrNull())
        val startDate = eventStartDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val startTime = eventStartTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val endDate = eventEndDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val endTime = eventEndTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val location = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longi = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityUser = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val stateUser = state.toRequestBody("text/plain".toMediaTypeOrNull())
        val zipCodeUser = zipCode.toRequestBody("text/plain".toMediaTypeOrNull())
        val type = userType.toRequestBody("text/plain".toMediaTypeOrNull())
        emit(Resource.Loading)
        emit(safeApiCall { api.createEventOwnerRequest(id,title,description,startDate,startTime,endDate,
            endTime,location,lat,longi,cityUser,stateUser,zipCodeUser,type,image) })
    }.flowOn(Dispatchers.IO)

    override suspend fun updateRegistrationRequest(
        userId: String,
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
        imageDoc: List<MultipartBody.Part>?
    ): Flow<Resource<CommonResponse>> = flow{
        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val busName = businessName.toRequestBody("text/plain".toMediaTypeOrNull())
        val provName = providerName.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val category = businessCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = businessAddress.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longi = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityUser = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val stateUser = state.toRequestBody("text/plain".toMediaTypeOrNull())
        val zipCodeUser = zipCode.toRequestBody("text/plain".toMediaTypeOrNull())
        val webUrl = websiteUrl.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = contactNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        val days = availableDays.toRequestBody("text/plain".toMediaTypeOrNull())
        val time = availableTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val bioUser = bio.toRequestBody("text/plain".toMediaTypeOrNull())
        emit(Resource.Loading)
        emit(safeApiCall { api.updateRegistrationRequest(id,busName,provName,email,businessLogo,category,address,lat,longi,cityUser,stateUser,zipCodeUser,webUrl,phone,
            days,time,bioUser,imageDoc) })
    }.flowOn(Dispatchers.IO)



    override suspend fun petListRequest(userId: String): Flow<Resource<PetListModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.petListRequest(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun termsConditionRequest(): Flow<Resource<TermsConditionModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.termsConditionRequest() })
    }

    override suspend fun logOutRequest(userId:String): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.logOutRequest(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun helpSupportRequest(): Flow<Resource<TermsConditionModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.helpSupportRequest() })
    }

  override suspend fun privacyPolicyRequest(): Flow<Resource<TermsConditionModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.privacyPolicyRequest() })
    }
    override suspend fun aboutUsRequest(): Flow<Resource<TermsConditionModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.aboutUsRequest() })
    }

    override suspend fun verifyForgotOtp(
        emailOrPhone: String,
        otp: String
    ): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.verifyForgotOtp(emailOrPhone,otp) })
    }

    override suspend fun resetPassword(
        emailOrPhone: String,
        password: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.resetPassword(emailOrPhone,password) })
    }

    override suspend fun resendOTP(
        emailOrPhone: String,
        apiType: String
    ): Flow<Resource<OtpResponse>> = flow {
        emit(Resource.Loading)
//        emit(safeApiCall { api.resendPassword(emailOrPhone,apiType) })
        emit(safeApiCall { api.sendOtp(emailOrPhone,apiType) })
    }

    override suspend fun addPets(
        userId: String,
        petName: String,
        petBreed: String,
        petAge: String,
        petBio: String,
        pet_image: List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>> = flow{

        emit(Resource.Loading)

        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val name = petName.toRequestBody("text/plain".toMediaTypeOrNull())
        val breed = petBreed.toRequestBody("text/plain".toMediaTypeOrNull())
        val age = petAge.toRequestBody("text/plain".toMediaTypeOrNull())
        val bio = petBio.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call safeApiCall wrapper
        val result = safeApiCall {
            api.addPets(
                userId = id,
                petName = name,
                petBreed = breed,
                petAge = age,
                petBio = bio,
                pet_image = pet_image
            )
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun ownerDetail(userId: String): Flow<Resource<OwnerDetailsResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getOwnerDetail(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun updateOwnerDetail(
        userId: String,
        name: String,
        email: String,
        phone: String,
        bio: String,
        parent_type: String,
        profile_image:  List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>> = flow{

        emit(Resource.Loading)

        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val userName = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val userEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val userPhone = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val userBio = bio.toRequestBody("text/plain".toMediaTypeOrNull())
        val userParentType = parent_type.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call safeApiCall wrapper
        val result = safeApiCall {
            api.updateOwnerDetail(
                userId = id,
                name = userName,
                email = userEmail,
                phone = userPhone,
                bio = userBio,
                userParentType,
                profile_image = profile_image
            )
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getBusinessDetail(userId: String): Flow<Resource<OwnerDetailsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getBusinessDetail(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getBusinessProfile(userId: String): Flow<Resource<BusinessProfileModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getBusinessProfile(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getBusinessDashboard(userId: String): Flow<Resource<BusinessDetailsModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getBusinessDashboard(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun servicesListRequest(userId: String): Flow<Resource<ServicesListModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.servicesListRequest(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun eventListRequest(
        userId: String,
        page: String,
        type: String
    ): Flow<Resource<EventModel>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.eventListRequest(userId,page,type) })
    }.flowOn(Dispatchers.IO)

    override suspend fun registerAndUpdateBusiness(
        userId: String,
        name: String,
        email: String,
        contactNumber: String,
        businessLogo: List<MultipartBody.Part>,
        businessAddress: String,
        businessCategory: String,
        city: String,
        state: String,
        zipCode: String,
        latitude: String,
        longitude: String,
        websiteUrl: String,
        availableDays: String,
        availableTime: String,
        verification_docs: List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>> = flow<Resource<CommonResponse>> {

        emit(Resource.Loading)

        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessName = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessContact = contactNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        val category = businessCategory.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = businessAddress.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessCity = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessState = state.toRequestBody("text/plain".toMediaTypeOrNull())
        val businessZip = zipCode.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val long = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val webUrl = websiteUrl.toRequestBody("text/plain".toMediaTypeOrNull())
        val availDays = availableDays.toRequestBody("text/plain".toMediaTypeOrNull())
        val availTime= availableTime.toRequestBody("text/plain".toMediaTypeOrNull())


        val result = safeApiCall {
            api.registerAndUpdateBusiness(
                userId = id,
                name = businessName,
                email = businessEmail,
                contactNumber = businessContact,
                businessLogo = businessLogo,
                businessCategory = category,
                businessAddress = address,
                city = businessCity,
                state = businessState,
                zipCode = businessZip,
                latitude = lat,
                longitude = long,
                websiteUrl = webUrl,
                availableDays = availDays,
                availableTime = availTime,
                verification_docs = verification_docs
            )
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun addAndUpdateServices(
        userId: String,
        serviceTitle: String,
        description: String,
        images: List<MultipartBody.Part>?,
        price: String,
        serviceId: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        // Convert text params to RequestBody
        val id   = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val title = serviceTitle.toRequestBody("text/plain".toMediaTypeOrNull())
        val desc  = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val servicePrice= price.toRequestBody("text/plain".toMediaTypeOrNull())
        val sId  = serviceId.toRequestBody("text/plain".toMediaTypeOrNull())
        // Perform safe API call
        val result = safeApiCall {
            api.addAndUpdateService(
                userId = id,
                serviceTitle = title,
                description = desc,
                images = images,
                price = servicePrice,
                serviceId = sId
            )
        }

        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getFollowerList(userId: String,page: String,limit: String, search : String): Flow<Resource<FollowersResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getFollowerList(userId,page,limit,search) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getFollowingList(userId: String,page: String,limit: String, search : String): Flow<Resource<FollowingResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getFollowingList(userId,page,limit,search) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getBusinessProfileDetail(userId: String): Flow<Resource<BusinessDetailsResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getBusinessProfileDetail(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getMyPostDetails(userId: String,page:String): Flow<Resource<MyPostsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getMyPostDetail(userId,page) })
    }.flowOn(Dispatchers.IO)

    override suspend fun addAndRemoveFollowers(
        userId: String,
        followerId: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall(showLoader = false) { api.addAndRemoveFollowers(userId,followerId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getOwnerProfileDetails(userId: String,petOwnerId : String): Flow<Resource<PetOwnerDetailsResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getOwnerProfile(userId,petOwnerId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getOwnerGalleryPost(userId: String,page: String): Flow<Resource<OwnerGalleryResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall(showLoader = false) { api.getOwnerGallery(userId,page) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getPetDetail(petId: String): Flow<Resource<UpdatePetModel>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getPetDetail(petId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getHomeFeed(userId: String,page: String): Flow<Resource<HomeFeedResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.homeFeed(userId,page) })
    }.flowOn(Dispatchers.IO)

    override suspend fun createAd(
        userId: String,
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
        image: List<MultipartBody.Part>?
    ): Flow<Resource<CommonResponse>>  = flow {
        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val adTitle = adTitle.toRequestBody("text/plain".toMediaTypeOrNull())
        val adDescription = adDescription.toRequestBody("text/plain".toMediaTypeOrNull())
        val category = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val service = service.toRequestBody("text/plain".toMediaTypeOrNull())
        val expiry_date = expiry_date.toRequestBody("text/plain".toMediaTypeOrNull())
        val expiry_time = expiry_time.toRequestBody("text/plain".toMediaTypeOrNull())
        val termAndConditions = termAndConditions.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longi = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val serviceLocation = serviceLocation.toRequestBody("text/plain".toMediaTypeOrNull())
        val contactNumber = contactNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        val budget = budget.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile_visual = mobile_visual.toRequestBody("text/plain".toMediaTypeOrNull())
        emit(Resource.Loading)
        emit(safeApiCall { api.createAd(userId= id,adTitle = adTitle,adDescription = adDescription,category = category,
            service = service,expiry_date = expiry_date,expiry_time = expiry_time,termAndConditions = termAndConditions,
            latitude = lat, longitude = longi, serviceLocation = serviceLocation, contactNumber = contactNumber,
            budget = budget, mobile_visual = mobile_visual,image) })

    }.flowOn(Dispatchers.IO)

    override suspend fun savePost(userId: String, postId: String,
                                  eventId: String,
                                 addId: String): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.savePost(userId,postId,eventId,addId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun reportPost(
        userId: String,
        postId: String,
        reportReason: String,
        message: String
    ): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.reportPost(userId,postId,reportReason,message) })
    }.flowOn(Dispatchers.IO)

    override suspend fun postLikeUnlike(
        userId: String,
        postId: String,
        eventId: String,
        addId: String
    ): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall (false,{ api.postLikeUnlike(userId,postId,eventId,addId) }))
    }.flowOn(Dispatchers.IO)


    override suspend fun getComments(
        userId: String,
        postId: String,
        addId: String,
        page: String,
        limit: String
    ): Flow<Resource<CommentsResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall( false) { api.getComments(userId, postId, addId, page, limit) })
    }.flowOn(Dispatchers.IO)

    override suspend fun addNewComment(
        userId: String,
        postId: String,
        commentText: String,
        addId: String
    ): Flow<Resource<AddCommentResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.addNewComment(userId,postId,commentText,addId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun replyComment(
        userId: String,
        postId: String,
        commentId: String,
        commentText: String
    ): Flow<Resource<AddCommentReplyResponse>>  = flow{
        emit(Resource.Loading)
        emit(safeApiCall (false) { api.replyComment(userId, postId, commentId, commentText) })
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteComment(
        userId: String,
        commentId: String
    ): Flow<Resource<CommonResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.deleteComment(userId,commentId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun editComment(
        userId: String,
        commentId: String,
        commenText: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.editComment(userId,commentId,commenText) })
    }.flowOn(Dispatchers.IO)

    override suspend fun commentLike(
        userId: String,
        commentId: String
    ): Flow<Resource<CommonResponse>> =  flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.commentLike(userId,commentId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun editPost(
        userId: String,
        postId: String,
        postDescription: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.editPost(userId,postId,postDescription) })
    }.flowOn(Dispatchers.IO)


    override suspend fun getMySavedPosts(
        userId: String,
        page: String
    ): Flow<Resource<HomeFeedResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getMySavedPosts(userId,page) })
    }.flowOn(Dispatchers.IO)

    override suspend fun deletePost(
        userId: String,
        postId: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.deletePost(userId, postId) })
    }

        override suspend fun trendingHashtags(): Flow<Resource<TrendingHashtagsResponse>> = flow {
            emit(Resource.Loading)
            emit(safeApiCall { api.trendingHashTags() })
        }.flowOn(Dispatchers.IO)

        override suspend fun petNearMe(
            userId: String,
            lat: String,
            long: String,
            page: String,
            limit: String,
            search: String
        ): Flow<Resource<PetsResponse>> = flow {
            emit(Resource.Loading)
            emit(safeApiCall { api.discoverPetNearMe(userId, lat, long, page, limit, search) })
        }.flowOn(Dispatchers.IO)

        override suspend fun discoverActivities(
            id: String,
            page: String,
            limit: String,
            search: String
        ): Flow<Resource<HomeFeedResponse>> = flow {
            emit(Resource.Loading)
            emit(safeApiCall { api.discoverActivities(id, page, limit, search) })
        }.flowOn(Dispatchers.IO)

        override suspend fun discoverEvents(
            id: String,
            search: String,
            userType: String,
            page: String,
            limit: String
        ): Flow<Resource<HomeFeedResponse>> = flow {
            emit(Resource.Loading)
            emit(safeApiCall { api.discoverEvents(id, search, userType, page, limit) })
        }.flowOn(Dispatchers.IO)

        override suspend fun ownerService(
            userId: String,
            search: String
        ): Flow<Resource<ServicesResponse>> = flow {
            emit(Resource.Loading)
            emit(safeApiCall { api.ownerService(userId, search) })
        }.flowOn(Dispatchers.IO)

    override suspend fun ownerCategoryService(userId: String): Flow<Resource<CategoryResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.ownerCategoryService(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun ownerServiceDetail(businessUserId: String,userId: String): Flow<Resource<ServiceDetailsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.ownerServiceDetail(businessUserId,userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getOwnerServiceDetail(businessUserId: String) : Flow<Resource<ServiceDetailsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getOwnerServiceDetail(businessUserId)  })
    }.flowOn(Dispatchers.IO)


    override suspend fun deleteService(userId: Int, serviceId:Int) : Flow<Resource<DeleteResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.deleteService(userId,serviceId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun serviceReview(
        serviceId: Int,
        userId: Int,
        rating: String,
        message: String
    ): Flow<Resource<DeleteResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.serviceReview(serviceId,userId,rating,message) })
    }.flowOn(Dispatchers.IO)

    override suspend fun discoverPetPlaces(
        userId: String,
        search: String
    ): Flow<Resource<PetPlacesResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.discoverPetPlaces(userId,search) })
    }.flowOn(Dispatchers.IO)

    override suspend fun sponsoredAds(userId: String): Flow<Resource<BusinessAdsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getSponsoredAds(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getFAQ(): Flow<Resource<FaqResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getFAQ() })
    }.flowOn(Dispatchers.IO)

    override suspend fun friendList(userId: String): Flow<Resource<FriendListResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.friendList(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFollowFollower(
        type: String,
        userId: String,
        followerId: String
    ): Flow<Resource<CommonResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.removeFollowerFollowing(type,userId,followerId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getSubscription(userId: String): Flow<Resource<SubscriptionResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getSubscription(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun businessFollow(businessId: String): Flow<Resource<FollowStatusResponse>> =flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.businessFollow(businessId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun createChannel(
        senderId: Int,
        receiverId: String?,
        eventId: String?,
        chatId: String?,
        chatType: String?
    ): Flow<Resource<CreateChannelResponse>> = flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.createChannel(senderId,receiverId,eventId,chatId,chatType) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getUserImage(userId: String): Flow<Resource<UserImageResponse>> =flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.getUserImage(userId) })
    }.flowOn(Dispatchers.IO)

    override suspend fun joinCommunity(
        userId: List<String>,
        eventId: String
    ): Flow<Resource<JoinCommunityResponse>> =flow{
        emit(Resource.Loading)
        val request = JoinCommunityRequest(userId, eventId)
        emit(safeApiCall { api.joinCommunity(request) })
    }.flowOn(Dispatchers.IO)

    override suspend fun allChatList( userId :String): Flow<Resource<AllChatListResponse>> =flow{
        emit(Resource.Loading)
        emit(safeApiCall { api.allChatList(userId) })
    }.flowOn(Dispatchers.IO)

}

    /**
     * A reusable helper function to handle all API calls safely.
     */
    private suspend fun <T : BaseResponse> safeApiCall(showLoader: Boolean = true, apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            if(showLoader) {
                LoaderManager.show()
            }
            val response = apiCall()
            LoaderManager.hide()
            when {
                !response.isSuccessful -> {
                   // Resource.Error("Server error: ${response.code()} - ${response.message()}")
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = try {
                        gson.fromJson(errorJson, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                    Resource.Error(errorResponse?.message ?: "Server Error")
                }
                response.body() == null -> {
                    Resource.Error("Empty response body")
                }
                response.body()?.success == false -> {
                    Resource.Error(response.body()?.message ?: "Operation failed")
                }
                else -> {
                    Resource.Success(response.body()!!)
                }
            }
        } catch (e: Exception) {
            LoaderManager.hide()
            Log.d("@Error","*****"+e.message.toString())
            Resource.Error(e.message.toString())
        }
    }
