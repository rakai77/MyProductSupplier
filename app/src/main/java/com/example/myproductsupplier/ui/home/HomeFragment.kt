package com.example.myproductsupplier.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsupplier.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getToken()
        setupRecyclerView()
    }

    private fun getToken() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collect {
                    token = it
                    Log.d("Check Home token", token)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            val adapter = ProductPagingAdapter()
            rvProduct.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
            } else {
                binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
            }
            lifecycleScope.launch {
                viewModel.getALlStory("Bearer $token").collectLatest { adapter.submitData(viewLifecycleOwner.lifecycle, it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}