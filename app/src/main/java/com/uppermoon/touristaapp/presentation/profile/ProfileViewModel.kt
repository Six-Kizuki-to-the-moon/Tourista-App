package com.uppermoon.touristaapp.presentation.profile

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val userPreferences: UserPreferences): ViewModel() {

    fun clearToken() {
        viewModelScope.launch {
            userPreferences.clearToken()
        }
    }
}