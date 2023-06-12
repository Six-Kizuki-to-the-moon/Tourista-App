package com.uppermoon.touristaapp.presentation.profile

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val userRepository: UserRepository, private val destinationRepository: DestinationRepository ,private val userPreferences: UserPreferences): ViewModel() {

    fun postCreateProfile(
        imageMultipart: MultipartBody.Part,
        name: RequestBody,
        age: RequestBody,
        phoneNumber: RequestBody,
        address: RequestBody,
        lat: Int,
        lon: Int,
    )=
        userRepository.postCreateProfile(imageMultipart, name, age, phoneNumber, address, 1232321, 1214214)

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun saveToken(user: User) {
        viewModelScope.launch {
            userPreferences.saveToken(user)
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            userPreferences.clearToken()
        }
    }
}