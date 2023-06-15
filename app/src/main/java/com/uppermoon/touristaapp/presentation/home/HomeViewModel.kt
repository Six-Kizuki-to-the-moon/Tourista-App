package com.uppermoon.touristaapp.presentation.home

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.DestinationResult
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val destinationRepository: DestinationRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _listDestination = MutableLiveData<HomeState>()
    val listDestination: LiveData<HomeState> = _listDestination

    init {
        getPopularDestination()
    }

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun getPopularDestination() {
        destinationRepository.getPopularDestination().onEach {
            when (it) {
                is DestinationResult.Success -> {
                    _listDestination.value = HomeState(destination = it.data)
                }
                is DestinationResult.Loading -> {
                    _listDestination.value = HomeState(isLoading = true)
                }
                is DestinationResult.Error -> {
                    _listDestination.value = HomeState(error = it.error)


                }
            }
        }.launchIn(viewModelScope)
    }

    fun nearYouDestination(id: Int, userLat: Float, userLon: Float) = destinationRepository.postDestinationNearYou(id, userLat, userLon)
}