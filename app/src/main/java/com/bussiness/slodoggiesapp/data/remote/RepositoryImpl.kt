package com.bussiness.slodoggiesapp.data.remote

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.bussiness.slodoggiesapp.data.newModel.CommonResponse
import com.bussiness.slodoggiesapp.data.newModel.LoginResponse
import com.bussiness.slodoggiesapp.data.newModel.OtpResponse
import com.bussiness.slodoggiesapp.data.newModel.OwnerDetailsResponse
import com.bussiness.slodoggiesapp.data.newModel.RegisterResponse
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.LoaderManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: ApiService
) : Repository {

    override suspend fun sendOtp(
        emailOrPhone: String,
        apiType: String
    ): Flow<Resource<OtpResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.sendOtp(emailOrPhone, apiType) })
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
        emit(safeApiCall { api.userRegister(fullName, emailOrPhone, password, deviceType, fcm_token, userType, otp) })
    }.flowOn(Dispatchers.IO)

    override suspend fun userLogin(
        emailOrPhone: String,
        password: String,
        deviceType: String,
        fcm_token: String
    ): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.userLogin(emailOrPhone,password, deviceType, fcm_token) })
    }.flowOn(Dispatchers.IO)

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
        emit(safeApiCall { api.resendPassword(emailOrPhone,apiType) })
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
        profile_image:  List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>> = flow{

        emit(Resource.Loading)

        // Convert text params to RequestBody
        val id = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val userName = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val userEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val userPhone = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val userBio = bio.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call safeApiCall wrapper
        val result = safeApiCall {
            api.updateOwnerDetail(
                userId = id,
                name = userName,
                email = userEmail,
                phone = userPhone,
                bio = userBio,
                profile_image = profile_image
            )
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getBusinessDetail(userId: String): Flow<Resource<OwnerDetailsResponse>> = flow {
        emit(Resource.Loading)
        emit(safeApiCall { api.getBusinessDetail(userId) })
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

        // Call safeApiCall wrapper
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
        images: List<MultipartBody.Part>,
        price: String,
        serviceId: String
    ): Flow<Resource<CommonResponse>> = flow {

        emit(Resource.Loading)

        // Convert text params to RequestBody
        val id          = userId.toRequestBody()
        val title       = serviceTitle.toRequestBody()
        val desc        = description.toRequestBody()
        val servicePrice= price.toRequestBody()
        val sId         = serviceId.toRequestBody()

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

    override suspend fun createOwnerPost(
        fields: Map<String, String>,
        images: List<MultipartBody.Part>
    ): Flow<Resource<CommonResponse>> = flow {

        emit(Resource.Loading)

        val partMap = fields.mapValues { entry ->
            entry.value.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        emit(
            safeApiCall {
                api.createOwnerPost(partMap, images)
            }
        )
    }.flowOn(Dispatchers.IO)



    /**
     * A reusable helper function to handle all API calls safely.
     */
    private suspend fun <T : BaseResponse> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            LoaderManager.show()
            val response = apiCall()
            LoaderManager.hide()
            when {
                !response.isSuccessful -> {
                    Resource.Error("Server error: ${response.code()} - ${response.message()}")
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
        } catch (e: IOException) {
            Resource.Error("Network error, please check your connection")
        } catch (e: HttpException) {
            Resource.Error("HTTP error ${e.code()}: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}
