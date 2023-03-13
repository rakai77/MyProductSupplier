package com.example.myproductsupplier.data.remote.api

import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.local.entity.LoginEntity
import com.example.myproductsupplier.data.local.entity.RegisterEntity
import com.example.myproductsupplier.data.remote.response.AddProductResponse
import com.example.myproductsupplier.data.remote.response.LoginResponse
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/login")
    suspend fun login(
        @Body loginEntity: LoginEntity
    ): LoginResponse

    @POST("/auth/register")
    suspend fun register(
        @Body registerEntity: RegisterEntity
    ) : RegisterResponse

    @POST("/barang/create")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body addProductEntity: AddProductEntity
    ) : AddProductResponse

}