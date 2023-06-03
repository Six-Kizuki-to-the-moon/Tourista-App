package com.uppermoon.touristaapp.presentation.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch

class WelcomeViewModel(private val userPreferences: UserPreferences): ViewModel() {

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun saveToken(user: User) {
        viewModelScope.launch {
            userPreferences.saveToken(user)
        }
    }
}