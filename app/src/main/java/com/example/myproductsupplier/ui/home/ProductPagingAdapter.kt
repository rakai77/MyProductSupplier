package com.example.myproductsupplier.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myproductsupplier.data.remote.response.DataItem
import com.example.myproductsupplier.databinding.ListItemProductBinding

class ProductPagingAdapter : PagingDataAdapter<DataItem, ProductPagingAdapter.ProductViewHolder>(DIFF_CALLBACK){

    inner class ProductViewHolder(private val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            binding.apply {
                tvProductName.text = data.namaBarang
                tvPrice.text = data.harga.toString()
                tvStock.text = data.stok.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}