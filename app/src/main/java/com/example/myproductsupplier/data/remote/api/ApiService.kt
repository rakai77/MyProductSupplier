package com.example.myproductsupplier.data.remote.api

import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.data.local.entity.LoginEntity
import com.example.myproductsupplier.data.local.entity.RegisterEntity
import com.example.myproductsupplier.data.remote.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/barang/find-all")
    suspend fun getListProduct(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ) : GetProductResponse

    @POST("/supplier/create")
    suspend fun addSupplier(
        @Header("Authorization") token: String,
        @Body addSupplierEntity: AddSupplierEntity
    ) : AddSupplierResponse

    @GET("/supplier/find-all")
    suspend fun getListSupplier(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ) : GetSupplierResponse

    @GET("/barang/find-by-id/{id}")
    suspend fun getDetailProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : DetailProductResponse

}