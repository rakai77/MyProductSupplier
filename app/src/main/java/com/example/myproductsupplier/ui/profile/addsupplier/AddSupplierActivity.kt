package com.example.myproductsupplier.ui.profile.addsupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myproductsupplier.MainActivity
import com.example.myproductsupplier.R
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import com.example.myproductsupplier.databinding.ActivityAddSupplierBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class AddSupplierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSupplierBinding
    private val viewModel by viewModels<AddSupplierViewModel>()

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        setupAction()
    }


    private fun setupForm() : Boolean{
        var form = false

        val supplierName = binding.edtNameSupplier.text.toString()
        val phone = binding.edtTelpSupplier.text.toString()
        val address = binding.edtAddressSupplier.text.toString()

        when {
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
        viewModel.addSupplier.observe(this@AddSupplierActivity) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        this@AddSupplierActivity,
                        "${result.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AddSupplierActivity, MainActivity::class.java))
                    finish()
                }
                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, RegisterResponse::class.java)

                    Toast.makeText(
                        this@AddSupplierActivity,
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
                        supplierName = edtNameSupplier.text.toString(),
                        phone = edtTelpSupplier.text.toString(),
                        address = edtAddressSupplier.text.toString()
                    )
                }
            }
        }
    }
}