package com.example.myproductsupplier.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetProductResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("total_page")
	val totalPage: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("total_record")
	val totalRecord: Int? = null
)

data class SupplierData(

	@field:SerializedName("namaSupplier")
	val namaSupplier: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)

data class DataItem(

	@field:SerializedName("harga")
	val harga: Double? = null,

	@field:SerializedName("supplier")
	val supplier: SupplierData? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaBarang")
	val namaBarang: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null
)
