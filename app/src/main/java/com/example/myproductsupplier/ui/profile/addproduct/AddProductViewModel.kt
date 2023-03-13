package com.example.myproductsupplier.ui.profile.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.remote.response.AddProductResponse
import com.example.myproductsupplier.data.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val repo: ProductRepository, private val pref: UserPreference) : ViewModel() {

    private val _addProduct = MutableLiveData<Resource<AddProductResponse>>()
    val addProduct: LiveData<Resource<AddProductResponse>> = _addProduct

    val getToken = pref.getToken()

    fun addProduct(token: String, productName: String, stock: Int, price: Double, supplierName: String, phone: String, address: String) {
        repo.addProduct(token, AddProductEntity(productName, stock, price, supplierName, phone, address)).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _addProduct.value = Resource.Loading
                }
                is Resource.Success -> {
                    result.data.let {
                        _addProduct.value = Resource.Success(it)
                    }
                }
                is Resource.Error -> {
                    _addProduct.value = Resource.Error(result.message, result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }
}