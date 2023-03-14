package com.example.myproductsupplier.data.repository.product

import androidx.paging.PagingData
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.remote.response.AddProductResponse
import com.example.myproductsupplier.data.remote.response.DataItem
import com.example.myproductsupplier.data.remote.response.GetProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun addProduct(token: String, productEntity: AddProductEntity) : Flow<Resource<AddProductResponse>>

    fun getListProduct(token: String) : Flow<PagingData<DataItem>>
}