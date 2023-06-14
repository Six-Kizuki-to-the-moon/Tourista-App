package com.uppermoon.touristaapp.presentation.profile

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.ProfilePreferences
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.Profile
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun postCreateProfile(
        token: String,
        imageMultipart: MultipartBody.Part,
        name: RequestBody,
        age: RequestBody,
        phoneNumber: RequestBody,
        address: RequestBody,
        lat: RequestBody,
        lon: RequestBody,
    ) =
        userRepository.postCreateProfile(
            token,
            imageMultipart,
            name,
            age,
            phoneNumber,
            address,
            "2214.316656".toRequestBody(),
            "-12412.4221".toRequestBody()
        )

    fun getUserProfile(
        token: String,
        username: String
    ) = userRepository.getUserByUsername(token, username)

//    fun saveProfile(profile: Profile){
//        viewModelScope.launch {
//            profilePreferences.saveProfile(profile)
//        }
//    }
    fun clearToken() {
        viewModelScope.launch {
            userPreferences.clearToken()
        }
    }
}