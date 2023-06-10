package com.uppermoon.touristaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DetailUserResponse
import com.uppermoon.touristaapp.data.network.response.LoginResponse
import com.uppermoon.touristaapp.data.network.response.RegisterResponse

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

    fun getUserById(
        token: String,
        id: String
    ): LiveData<UserResult<DetailUserResponse>> = liveData {
        emit(UserResult.Loading)
        try {
            val getUserResponse = apiService.getUsersById("Bearer $token", id)
            emit(UserResult.Success(getUserResponse))
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