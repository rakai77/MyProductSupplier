package com.example.myproductsupplier.ui.supplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myproductsupplier.data.remote.response.DataItemSupplier
import com.example.myproductsupplier.databinding.ListItemSupplierBinding

class SupplierPagingAdapter : PagingDataAdapter<DataItemSupplier, SupplierPagingAdapter.SupplierViewHolder>(DIFF_CALLBACK) {

    inner class SupplierViewHolder(private val binding: ListItemSupplierBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemSupplier) {
            binding.apply {
                tvSupplierName.text = data.namaSupplier
                tvPhone.text = data.noTelp
                tvAddress.text = data.alamat
            }
        }
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val binding = ListItemSupplierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SupplierViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemSupplier>() {
            override fun areItemsTheSame(oldItem: DataItemSupplier, newItem: DataItemSupplier): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemSupplier,
                newItem: DataItemSupplier
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}