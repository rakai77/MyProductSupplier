package com.example.myproductsupplier.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myproductsupplier.data.local.entity.AddSupplierEntity
import com.example.myproductsupplier.databinding.FragmentProfileBinding
import com.example.myproductsupplier.ui.auth.AuthActivity
import com.example.myproductsupplier.ui.auth.AuthViewModel
import com.example.myproductsupplier.ui.profile.addproduct.AddProductActivity
import com.example.myproductsupplier.ui.profile.addsupplier.AddSupplierActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataUser()
        setupAction()
        logout()
    }

    private fun getDataUser() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getUsername.collect {
                        binding.tvUsername.text = it
                        Log.d("Check username", it)
                    }
                }
                launch {
                    viewModel.getProfileName.collect {
                        binding.tvProfileName.text = it
                        Log.d("Check profile name", it)
                    }
                }
            }
        }
    }

    private fun logout() {
        binding.tflLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Toast.makeText(
                requireContext(),
                "Logout Successfully",
                Toast.LENGTH_LONG
            ).show()
            activity?.finish()
        }
    }

    private fun setupAction() {
        binding.apply {
            tflAddProduct.setOnClickListener {
                startActivity(Intent(requireActivity(), AddProductActivity::class.java))
            }
            tflAddSupplier.setOnClickListener {
                startActivity(Intent(requireActivity(), AddSupplierActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}