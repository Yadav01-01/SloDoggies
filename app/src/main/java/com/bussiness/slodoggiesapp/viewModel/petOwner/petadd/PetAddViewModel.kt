package com.bussiness.slodoggiesapp.viewModel.petOwner.petadd

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.petlist.Data
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetAddUpDateUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipart
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.petOwner.petlist.PetListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetAddViewModel @Inject constructor(private val repository: Repository, private var sessionManager: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow(PetAddUpDateUiState())
    val uiState: StateFlow<PetAddUpDateUiState> = _uiState
    var  dataType : Data? = Data()


    val ageOptions = listOf("< than 1 Year") + (1..20).map { "$it Year" }

    fun onPetNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onPetBreedChange(breed: String) {
        _uiState.value = _uiState.value.copy(breed = breed)
    }

    fun onPetAgeChange(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun onPetBioChange(bio: String) {
        _uiState.value = _uiState.value.copy(bio = bio)
    }

    fun onPetImageChange(image: Uri) {
        _uiState.value = _uiState.value.copy(image = image)
    }

    fun updatePet(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value
        if (state.name==null){
            onError(Messages.PET_NAME)
            return
        }
        if (state.breed==null){
            onError(Messages.BREED_NAME)
            return
        }
        if (state.bio==null){
            onError(Messages.BIO_SMS)
            return
        }
        val imagePart = state.image?.let { createSingleMultipart(context,uri = it, keyName = "pet_image") }
        viewModelScope.launch {
            repository.updatePetRequest(
                petName = state.name?:"",
                petBreed = state.breed?:"",
                petAge = state.age?:"",
                petBio = state.bio?:"",
                petId = "",
                userId = sessionManager.getUserId(),
                image = imagePart,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                val data= response.data
//                                onValueChange(data.pet_name,data.pet_breed,data.pet_age,data.pet_bio,data.pet_image,data.id.toString())
                                val dataModel= Data(id = data.id,
                                    pet_age=data.pet_age,
                                    pet_bio=data.pet_bio,
                                    pet_breed=data.pet_breed,
                                    pet_image=data.pet_image,
                                    pet_name=data.pet_name)
                                setData(dataModel)
                                clearState()
                                onSuccess()

                            } else {
                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }
    }



    fun onValueChange(name: String? = null, breed: String? = null, age: String? = null, bio: String? = null, image: String? = null, id: String? = null) {
        _uiState.value = _uiState.value.copy(
            name = name ?: _uiState.value.name,
            breed = breed ?: _uiState.value.breed,
            age = age ?: _uiState.value.age,
            bio = bio ?: _uiState.value.bio,
            id = id ?: _uiState.value.id,
            image = image?.toUri() ?: _uiState.value.image
        )
    }

    fun setData(data:Data?){
        dataType=data
    }

    fun getData():Data?{
        return dataType
    }


    fun clearState() {
        _uiState.value = PetAddUpDateUiState()
    }
}



