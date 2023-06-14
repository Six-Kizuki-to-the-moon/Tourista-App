package com.uppermoon.touristaapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.UserRepository
import com.uppermoon.touristaapp.di.Injection
import com.uppermoon.touristaapp.presentation.detail.DetailViewModel
import com.uppermoon.touristaapp.presentation.explore.ExploreViewModel
import com.uppermoon.touristaapp.presentation.home.HomeViewModel
import com.uppermoon.touristaapp.presentation.login.LoginViewModel
import com.uppermoon.touristaapp.presentation.profile.ProfileViewModel
import com.uppermoon.touristaapp.presentation.register.RegisterViewModel
import com.uppermoon.touristaapp.presentation.welcome.WelcomeViewModel

class ViewModelFactory constructor(
    private val userRepository: UserRepository,
    private val destinationRepository: DestinationRepository,
    private val pref: UserPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }else if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository, pref) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository,destinationRepository,pref) as T
        }else if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(userRepository,destinationRepository, pref) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository,pref) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(destinationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, destinationRepository: DestinationRepository, pref: UserPreferences): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context),destinationRepository, pref)
            }.also { instance = it }
    }
}