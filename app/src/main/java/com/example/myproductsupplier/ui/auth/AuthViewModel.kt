package com.example.myproductsupplier.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.LoginEntity
import com.example.myproductsupplier.data.local.entity.RegisterEntity
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.remote.response.LoginResponse
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import com.example.myproductsupplier.data.repository.authentication.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository, private val pref: UserPreference) : ViewModel(){

    private val _loginState = MutableLiveData<Resource<LoginResponse>>()
    val loginState: LiveData<Resource<LoginResponse>> = _loginState

    private val _registerState = MutableLiveData<Resource<RegisterResponse>>()
    val registerState: LiveData<Resource<RegisterResponse>> = _registerState

    val getToken = pref.getToken()
    val getProfileName = pref.getProfileName()
    val getUsername = pref.getUsername()

    fun login(username: String, password: String) {
        repo.login(LoginEntity(username, password)).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _loginState.value = Resource.Loading
                }
                is Resource.Success -> {
                    result.data.let {
                        _loginState.value = Resource.Success(it)
                        pref.saveToken(it.data?.token.toString())
                        pref.saveProfileName(it.data?.profileName.toString())
                        pref.saveUsername(it.data?.username.toString())
                        Log.d("Check save username, token, email", "${result.data}")
                    }
                }
                is Resource.Error -> {
                    _loginState.value = Resource.Error(result.message,  result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun register(profileName: String, username: String, password: String) {
        repo.register(RegisterEntity(profileName, username, password)).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _registerState.value = Resource.Loading
                }
                is Resource.Success -> {
                    result.data.let {
                        _registerState.value = Resource.Success(it)
                    }
                }
                is Resource.Error -> {
                    _registerState.value = Resource.Error(result.message,  result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}