package com.uppermoon.touristaapp.presentation.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.network.response.TripRecommendationResponseItem
import com.uppermoon.touristaapp.data.preferences.ProfilePreferences
import com.uppermoon.touristaapp.presentation.home.HomeState

class RecommendViewModel(
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    private val _listDestination = MutableLiveData<List<TripRecommendationResponseItem>>()
    val listDestination: LiveData<List<TripRecommendationResponseItem>> = _listDestination

    fun getRecommendTripDestination(id: Int) = destinationRepository.getTripDetail(id)
}