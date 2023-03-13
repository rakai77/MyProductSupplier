package com.example.myproductsupplier.data.repository.authentication

import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.LoginEntity
import com.example.myproductsupplier.data.local.entity.RegisterEntity
import com.example.myproductsupplier.data.remote.response.LoginResponse
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(loginEntity: LoginEntity) : Flow<Resource<LoginResponse>>

    fun register(registerEntity: RegisterEntity) : Flow<Resource<RegisterResponse>>
}