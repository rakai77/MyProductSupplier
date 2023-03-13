package com.example.myproductsupplier.data.repository.product

import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.remote.response.AddProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun addProduct(token: String, productEntity: AddProductEntity) : Flow<Resource<AddProductResponse>>


}