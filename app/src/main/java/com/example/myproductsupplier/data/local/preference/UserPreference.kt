package com.example.myproductsupplier.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.myproductsupplier.utils.PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")


class UserPreference(private val dataStore: DataStore<Preferences>) {

    fun getToken(): Flow<String> {
        return dataStore.data.map { preference ->
            preference[PreferenceKey.TOKEN] ?: ""
        }
    }

    suspend fun saveToken(message: String) {
        dataStore.edit { preference ->
            preference[PreferenceKey.TOKEN] = message
        }
    }

    fun getProfileName() : Flow<String> {
        return dataStore.data.map { preference ->
            preference[PreferenceKey.PROFILE_NAME] ?: ""
        }
    }

    suspend fun saveProfileName(message: String) {
        dataStore.edit { preference ->
            preference[PreferenceKey.PROFILE_NAME] = message
        }
    }

    fun getUsername() : Flow<String> {
        return dataStore.data.map { preference ->
            preference[PreferenceKey.USERNAME] ?: ""
        }
    }

    suspend fun saveUsername(message: String) {
        dataStore.edit { preference ->
            preference[PreferenceKey.USERNAME] = message
        }
    }

    suspend fun logout() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }
}