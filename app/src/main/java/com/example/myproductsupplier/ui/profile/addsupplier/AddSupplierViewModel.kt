package com.example.myproductsupplier.ui.profile.addsupplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.remote.response.AddSupplierResponse
import com.example.myproductsupplier.data.repository.supplier.SupplierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddSupplierViewModel @Inject constructor(private val repo: SupplierRepository, pref: UserPreference) : ViewModel() {

    private val _addSupplier = MutableLiveData<Resource<AddSupplierResponse>>()
    val addSupplier: LiveData<Resource<AddSupplierResponse>> = _addSupplier

    val getToken = pref.getToken()

    fun addProduct(token: String, supplierName: String, phone: String, address: String) {
        repo.addSupplier(token, AddSupplierEntity(supplierName, phone, address)).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _addSupplier.value = Resource.Loading
                }
                is Resource.Success -> {
                    result.data.let {
                        _addSupplier.value = Resource.Success(it)
                    }
                }
                is Resource.Error -> {
                    _addSupplier.value = Resource.Error(result.message, result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }
}