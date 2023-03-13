package com.example.myproductsupplier.data.repository.product

import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.remote.api.ApiService
import com.example.myproductsupplier.data.remote.response.AddProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(private val apiService: ApiService) : ProductRepository {

    override fun addProduct(
        token: String,
        productEntity: AddProductEntity
    ): Flow<Resource<AddProductResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.addProduct(token, productEntity)
            emit(Resource.Success(result))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    400 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    401 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    403 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    404 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    422 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    500 -> emit(Resource.Error(t.message(), t.response()?.errorBody()))
                    else -> emit(Resource.Error(null, null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage, null))
        }
    }

}