package com.example.myproductsupplier.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ProductRepository, pref: UserPreference) : ViewModel(){

    val token = pref.getToken()

    fun getListProduct(token: String) = repo.getListProduct(token).cachedIn(viewModelScope)
}