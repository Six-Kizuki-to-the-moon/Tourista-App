package com.uppermoon.touristaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DestinationRepository(private val apiService: ApiService) {

    fun getPopularDestination(): Flow<DestinationResult<List<DestinationResponseItem>>> = flow {
        emit(DestinationResult.Loading)
        try {
            val popularDestinationResponse = apiService.getPopularDestination()
            if (popularDestinationResponse.isNotEmpty()) {
                emit(DestinationResult.Success(popularDestinationResponse))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(DestinationResult.Error(e.toString()))
        }
    }

    fun postDestinationNearYou(
        id: Int,
        userLat: Float,
        userLon: Float
    ): LiveData<DestinationResult<List<RecommendationsItem>>> = liveData {
        emit(DestinationResult.Loading)
        try {
            val nearYouDestinationResponse = apiService.nearYou(id, userLat, userLon)
            val listRecommend = nearYouDestinationResponse.recommendations
            if (listRecommend.isNotEmpty()) {
                emit(DestinationResult.Success(listRecommend))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DestinationResult.Error(e.toString()))
        }
    }

    fun postUserRecommendation(
        id: Int,
        city: String,
        category: String,
        price: Int
    ): LiveData<DestinationResult<RecommendationContentResponse>> = liveData {
        emit(DestinationResult.Loading)
        try {
            val userRecommendationResponse =
                apiService.userRecommendation(id, category, city, price)
            emit(DestinationResult.Success(userRecommendationResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DestinationResult.Error(e.toString()))
        }
    }

    fun postSimiliarItem(destinatonName: String): Flow<DestinationResult<List<RecommendationsItem>>> =
        flow {
            emit(DestinationResult.Loading)
            try {
                val similiarItemResponse = apiService.similiarItem(destinatonName)
                emit(DestinationResult.Success(similiarItemResponse))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DestinationResult.Error(e.toString()))
            }
        }

    fun getTripDetail(id: Int): LiveData<DestinationResult<TripRecommendationResponse>> = liveData {
        emit(DestinationResult.Loading)
        try {
            val tripRecommendationResponse = apiService.getTripDetail(id)
            emit(DestinationResult.Success(tripRecommendationResponse))
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