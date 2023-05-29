package com.uppermoon.touristaapp.di

import android.content.Context
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.network.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}