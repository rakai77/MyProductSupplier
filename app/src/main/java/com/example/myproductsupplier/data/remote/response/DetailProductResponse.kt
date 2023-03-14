package com.example.myproductsupplier.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("data")
	val data: DataDetailProduct? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataDetailProduct(

	@field:SerializedName("harga")
	val harga: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaBarang")
	val namaBarang: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null
)
