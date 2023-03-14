package com.example.myproductsupplier.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myproductsupplier.data.remote.api.ApiService
import com.example.myproductsupplier.data.remote.response.DataItemSupplier
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SupplierPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val token: String
) : PagingSource<Int, DataItemSupplier>(){

    override fun getRefreshKey(state: PagingState<Int, DataItemSupplier>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemSupplier> {
        return try {
            val supplierOffset = params.key ?: OFFSET

            val response = apiService.getListSupplier(token, 20, supplierOffset)
            val supplierResult = response.data ?: emptyList()

            val nextKey = if (supplierResult.isEmpty()) null else supplierOffset.plus(TOTAL_ITEM)

            LoadResult.Page(
                data = supplierResult,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val OFFSET = 0
        const val TOTAL_ITEM = 5
    }
}