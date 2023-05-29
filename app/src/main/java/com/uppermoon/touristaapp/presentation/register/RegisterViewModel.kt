package com.uppermoon.touristaapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uppermoon.touristaapp.data.UserRepository

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postRegister(username: String, email: String, password: String, confirmPassword: String) =
        userRepository.postRegister(username, email, password, confirmPassword)
}