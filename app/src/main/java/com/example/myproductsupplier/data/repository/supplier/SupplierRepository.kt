package com.example.myproductsupplier.data.repository.supplier

import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.data.remote.response.AddSupplierResponse
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {

    fun addSupplier(token: String, addSupplierEntity: AddSupplierEntity) : Flow<Resource<AddSupplierResponse>>
}