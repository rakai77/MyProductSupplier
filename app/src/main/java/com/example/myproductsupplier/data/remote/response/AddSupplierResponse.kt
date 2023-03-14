package com.example.myproductsupplier.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddSupplierResponse(

	@field:SerializedName("data")
	val data: DataSupplier? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataSupplier(

	@field:SerializedName("namaSupplier")
	val namaSupplier: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
