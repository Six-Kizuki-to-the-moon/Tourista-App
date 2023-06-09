package com.uppermoon.touristaapp.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.uppermoon.touristaapp.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getToken(): Flow<User> {
        return dataStore.data.map {
            User(
                it[USERNAME] ?: "username",
                it[TOKEN] ?: "token"
            )
        }
    }

    suspend fun saveToken(user: User) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = user.username
            preferences[TOKEN] = user.token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USERNAME = stringPreferencesKey("username")
        private val TOKEN = stringPreferencesKey("token")


        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}