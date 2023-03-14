package com.example.myproductsupplier.ui.home.detail

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.remote.response.DataDetailProduct
import com.example.myproductsupplier.data.remote.response.DetailProductResponse
import com.example.myproductsupplier.databinding.ActivityDetailProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private val viewModel by viewModels<DetailProductViewModel>()

    private var token: String = ""
    private var id: Int = 0
    private lateinit var dataItem: DetailProductResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productID = intent.getIntExtra(PRODUCT_ID, 0)
        id = productID
        if (id.toString().isEmpty()) {
            val data: Uri? = intent.data
            val query = data?.getQueryParameter("id")
            if (query != null) {
                id = query.toInt()
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getToken.collect {
                        token = it
                    }
                }
                launch {
                    initObserver()
                }
            }
        }
    }


    private fun initObserver() {
        viewModel.getDetailProduct("Bearer $token", id)
        viewModel.detailProduct.observe(this@DetailProductActivity) { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    result.data.let { dataItem = it }
                    initData(result.data.data!!)
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun initData(dataItem: DataDetailProduct) {
        with(binding) {
            tvProductName.text = dataItem.namaBarang
            tvPrice.text = dataItem.harga.toString()
            tvStock.text = dataItem.stok.toString()
        }
    }

    companion object {
        const val PRODUCT_ID = "product_id"
    }
}