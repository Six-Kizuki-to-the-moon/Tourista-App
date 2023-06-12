package com.uppermoon.touristaapp.presentation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val userRepository: UserRepository,
    private val destinationRepository: DestinationRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun saveToken(user: User) {
        viewModelScope.launch {
            userPreferences.saveToken(user)
        }
    }

    fun getUserById(token: String, id: Int) = userRepository.getUserById(token,id)

    fun postUserRecommendation(id: Int, city: String, category: String, price: Int) =
        destinationRepository.postUserRecommendation(id, city, category, price)
}