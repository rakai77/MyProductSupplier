package com.example.myproductsupplier.data.repository.supplier

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.data.remote.ProductPagingSource
import com.example.myproductsupplier.data.remote.SupplierPagingSource
import com.example.myproductsupplier.data.remote.api.ApiService
import com.example.myproductsupplier.data.remote.response.AddSupplierResponse
import com.example.myproductsupplier.data.remote.response.DataItemSupplier
import com.example.myproductsupplier.data.remote.response.GetSupplierResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class SupplierRepositoryImp @Inject constructor(private val apiService: ApiService) : SupplierRepository {

    override fun addSupplier(token: String, addSupplierEntity: AddSupplierEntity): Flow<Resource<AddSupplierResponse>> = flow {
        emit(Resource.Loading)
        try {
            val result = apiService.addSupplier(token, addSupplierEntity)
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

    override fun getListSupplier(token: String): Flow<PagingData<DataItemSupplier>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
            ),
            pagingSourceFactory = { SupplierPagingSource(token = token, apiService = apiService) }
        ).flow
    }
}