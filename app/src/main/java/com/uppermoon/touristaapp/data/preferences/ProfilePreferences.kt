package com.uppermoon.touristaapp.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.uppermoon.touristaapp.domain.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfilePreferences private constructor(private val dataStore: DataStore<Preferences>){
    fun getProfile(): Flow<Profile> {
        return dataStore.data.map {
            Profile(
                it[ID] ?: 1,
            it[NAME] ?: ""
            )
        }
    }

    suspend fun saveProfile(profile: Profile) {
        dataStore.edit { preferences ->
            preferences[ID] = profile.id
            preferences[NAME] = profile.name
        }
    }

    suspend fun clearProfile() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ID = intPreferencesKey("1")
        private val NAME = stringPreferencesKey("name")

        @Volatile
        private var INSTANCE: ProfilePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ProfilePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ProfilePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}