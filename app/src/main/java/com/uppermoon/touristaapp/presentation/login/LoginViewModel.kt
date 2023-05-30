package com.uppermoon.touristaapp.presentation.login

import androidx.lifecycle.*
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, private val userPreferences: UserPreferences): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postLogin(email: String, password: String) =
        userRepository.postLogin(email, password)

    fun getToken(): LiveData<User> {
        return userPreferences.getToken().asLiveData()
    }

    fun saveToken(user: User) {
        viewModelScope.launch {
            userPreferences.saveToken(user)
        }
    }
}