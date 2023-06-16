package com.uppermoon.touristaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DetailUserResponse
import com.uppermoon.touristaapp.data.network.response.LoginResponse
import com.uppermoon.touristaapp.data.network.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository(private val apiService: ApiService) {

    fun postRegister(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<UserResult<RegisterResponse>> = liveData {
        emit(UserResult.Loading)
        try {
            val registerResponse =
                apiService.registerUser(username, email, password, confirmPassword)
            emit(UserResult.Success(registerResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UserResult.Error(e.toString()))
        }
    }

    fun postLogin(
        email: String,
        password: String
    ): LiveData<UserResult<LoginResponse>> = liveData {
        emit(UserResult.Loading)
        try {
            val loginResponse =
                apiService.loginUser(email, password)
            emit(UserResult.Success(loginResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UserResult.Error(e.toString()))
        }
    }

    fun getUserByUsername(
        token: String,
        username: String
    ): LiveData<UserResult<DetailUserResponse>> = liveData {
        emit(UserResult.Loading)
        try {
            val getUserResponse = apiService.getUsersByUsername("Bearer $token", username)
            emit(UserResult.Success(getUserResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UserResult.Error(e.toString()))
        }
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
    ): LiveData<UserResult<DetailUserResponse>> = liveData {
        emit(UserResult.Loading)
        try {
            val postUserProfile = apiService.createProfile("Bearer $token", imageMultipart, name, age, phoneNumber, address, lat, lon)
            emit(UserResult.Success(postUserProfile))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(UserResult.Error(e.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService)
        }.also { instance = it }
    }
}