package com.example.myproductsupplier.data.repository.supplier

import androidx.paging.PagingData
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.data.remote.response.AddSupplierResponse
import com.example.myproductsupplier.data.remote.response.DataItemSupplier
import com.example.myproductsupplier.data.remote.response.GetSupplierResponse
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {

    fun addSupplier(token: String, addSupplierEntity: AddSupplierEntity) : Flow<Resource<AddSupplierResponse>>

    fun getListSupplier(token: String) : Flow<PagingData<DataItemSupplier>>
}