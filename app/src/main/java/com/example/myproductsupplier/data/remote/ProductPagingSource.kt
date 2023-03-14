package com.example.myproductsupplier.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myproductsupplier.data.remote.api.ApiService
import com.example.myproductsupplier.data.remote.response.DataItem
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val apiService: ApiService,
    private var token: String
    ) : PagingSource<Int, DataItem>() {

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val productOffset = params.key ?: OFFSET

            val response = apiService.getListProduct(token, 20, productOffset)
            val productResult = response.data ?: emptyList()

            val nextKey = if (productResult.isEmpty()) null else productOffset.plus(TOTAL_ITEM)

            LoadResult.Page(
                data = productResult,
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