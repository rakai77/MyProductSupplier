package com.example.myproductsupplier.ui.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.repository.supplier.SupplierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(private val repo: SupplierRepository, pref: UserPreference) : ViewModel() {

    val token = pref.getToken()

    fun getListSupplier(token: String) = repo.getListSupplier(token).cachedIn(viewModelScope)
}