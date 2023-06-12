package com.uppermoon.touristaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DestinationResponse
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.data.network.response.RecommendationContentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DestinationRepository(private val apiService: ApiService) {

    fun getPopularDestination(): Flow<DestinationResult<List<DestinationResponseItem>>> = flow{
        emit(DestinationResult.Loading)
        try {
            val popularDestinationResponse = apiService.getPopularDestination()
            if (popularDestinationResponse.isNotEmpty()){
            emit(DestinationResult.Success(popularDestinationResponse))
            }

        } catch (e: Exception){
            e.printStackTrace()
            emit(DestinationResult.Error(e.toString()))
        }
    }

    fun postUserRecommendation(id: Int,city: String,category: String, price: Int): LiveData<DestinationResult<RecommendationContentResponse>> = liveData {
        emit(DestinationResult.Loading)
        try {
            val userRecommendationResponse = apiService.userRecommendation(id, category, city, price)
            emit(DestinationResult.Success(userRecommendationResponse))
        } catch (e: Exception) {
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