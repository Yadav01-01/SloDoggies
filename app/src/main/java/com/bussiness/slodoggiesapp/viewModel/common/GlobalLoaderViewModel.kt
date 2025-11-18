package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.util.LoaderManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalLoaderViewModel @Inject constructor() : ViewModel() {
    val isLoading = LoaderManager.isLoading
}
