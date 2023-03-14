package com.example.myproductsupplier.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.preference.UserPreference
import com.example.myproductsupplier.data.remote.response.DetailProductResponse
import com.example.myproductsupplier.data.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(private val repo: ProductRepository, pref: UserPreference) : ViewModel(){

    private val _detailProduct = MutableLiveData<Resource<DetailProductResponse>>()
    val detailProduct: LiveData<Resource<DetailProductResponse>> = _detailProduct

    val getToken = pref.getToken()

    fun getDetailProduct(token: String, id: Int) {
        repo.getDetailProduct(token, id).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _detailProduct.value = Resource.Loading
                }
                is Resource.Success -> {
                    result.data.let {
                        _detailProduct.value = Resource.Success(it)
                    }
                }
                is Resource.Error -> {
                    _detailProduct.value = Resource.Error(result.message, result.errorBody)
                }
            }
        }.launchIn(viewModelScope)
    }
}