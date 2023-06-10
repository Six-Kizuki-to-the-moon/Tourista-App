package com.uppermoon.touristaapp.presentation.profile

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val userRepository: UserRepository, private val userPreferences: UserPreferences): ViewModel() {

    fun postCreateProfile(
        imageMultipart: MultipartBody.Part,
        name: RequestBody,
        phoneNumber: RequestBody,
        address: RequestBody,
        lat: RequestBody,
        lon: RequestBody,
    ){
        userRepository.postCreateProfile(imageMultipart, name, phoneNumber, address, lat, lon)
    }

    fun clearToken() {
        viewModelScope.launch {
            userPreferences.clearToken()
        }
    }
}