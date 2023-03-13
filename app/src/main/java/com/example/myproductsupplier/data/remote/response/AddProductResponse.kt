package com.example.myproductsupplier.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddProductResponse(

	@field:SerializedName("data")
	val data: DataProduct? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Supplier(

	@field:SerializedName("namaSupplier")
	val namaSupplier: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)

data class DataProduct(

	@field:SerializedName("harga")
	val harga: Double? = null,

	@field:SerializedName("supplier")
	val supplier: Supplier? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaBarang")
	val namaBarang: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null
)
