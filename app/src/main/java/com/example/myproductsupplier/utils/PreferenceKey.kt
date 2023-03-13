package com.example.myproductsupplier.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {

    val TOKEN = stringPreferencesKey("token")
    val PROFILE_NAME = stringPreferencesKey("profile_name")
    val USERNAME = stringPreferencesKey("username")
}