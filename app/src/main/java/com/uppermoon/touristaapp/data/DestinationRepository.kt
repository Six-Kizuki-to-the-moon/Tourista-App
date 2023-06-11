package com.uppermoon.touristaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DestinationResponse

class DestinationRepository(private val apiService: ApiService) {

    fun getPopularDestination(): LiveData<DestinationResult<DestinationResponse>> = liveData{
        emit(DestinationResult.Loading)
        try {
            val popularDestinationResponse = apiService.getPopularDestination()
            emit(DestinationResult.Success(popularDestinationResponse))
        } catch (e: Exception){
            e.printStackTrace()
            emit(DestinationResult.Error(e.toString()))
        }

    }
    companion object {
        @Volatile
        private var instance: DestinationRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): DestinationRepository = instance ?: synchronized(this) {
            instance ?: DestinationRepository(apiService)
        }.also { instance = it }
    }
}