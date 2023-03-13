package com.example.myproductsupplier.ui.profile.addproduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myproductsupplier.MainActivity
import com.example.myproductsupplier.R
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.local.entity.AddProductEntity
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import com.example.myproductsupplier.databinding.ActivityAddProductBinding
import com.google.gson.Gson
import com.google.gson.JsonObject


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel by viewModels<AddProductViewModel>()

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        setupAction()

    }


    private fun setupForm() : Boolean{
        var form = false

        val productName = binding.edtNameProduct.text.toString()
        val stock = binding.edtStockProduct.text.toString()
        val price = binding.edtPriceProduct.text.toString()
        val supplierName = binding.edtNameSupplier.text.toString()
        val phone = binding.edtTelpSupplier.text.toString()
        val address = binding.edtAddressSupplier.text.toString()

        when {
            productName.isEmpty() -> {
                binding.tflNameProduct.error = "Please fill your product name."
            }
            stock.isEmpty() -> {
                binding.tflStockProduct.error = "Please fill your stock."
            }
            price.isEmpty() -> {
                binding.tflPriceProduct.error = "Please fill your price."
            }
            supplierName.isEmpty()-> {
                binding.tflNameSupplier.error = "Please fill your supplier name"
            }
            phone.isEmpty() -> {
                binding.tflTelpSupplier.error = "Please fill your phone number"
            }
            address.isEmpty() -> {
                binding.tflAddressSupplier.error = "Please fill your address"
            }
            else -> form = true
        }
        return form
    }


    private fun initObserver() {
        viewModel.addProduct.observe(this@AddProductActivity) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        this@AddProductActivity,
                        "${result.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AddProductActivity, MainActivity::class.java))
                    finish()
                }
                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, RegisterResponse::class.java)

                    Toast.makeText(
                        this@AddProductActivity,
                        "${errorResponse.status} ${errorResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getToken.collect {
                    token = it
                    Log.d("Check token in AddProductActivity", token)
                }
            }
        }

        binding.apply {
            btnSave.setOnClickListener {
                if (setupForm()) {
                    viewModel.addProduct(
                        "Bearer $token",
                        productName = edtNameProduct.text.toString(),
                        stock = edtStockProduct.text.toString().toInt(),
                        price = edtPriceProduct.text.toString().toDouble(),
                        supplierName = edtNameSupplier.text.toString(),
                        phone = edtTelpSupplier.text.toString(),
                        address = edtAddressSupplier.text.toString()
                    )
                }
            }
        }
    }
}