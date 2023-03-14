package com.example.myproductsupplier.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproductsupplier.databinding.ListItemProductBinding
import com.example.myproductsupplier.databinding.LoadingStatePagingBinding

class LoadingStateAdapter (private val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadViewHolder>(){

    inner class LoadViewHolder(private val binding: LoadingStatePagingBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnLoadAdapter.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbLoadAdapter.isVisible = loadState is LoadState.Loading
                btnLoadAdapter.isVisible = loadState is LoadState.Error
                tvLoadAdapter.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    tvLoadAdapter.text = loadState.error.localizedMessage
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding = LoadingStatePagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(binding)
    }
}