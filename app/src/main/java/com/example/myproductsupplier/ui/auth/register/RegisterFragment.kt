package com.example.myproductsupplier.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproductsupplier.R
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.remote.response.RegisterResponse
import com.example.myproductsupplier.databinding.FragmentRegisterBinding
import com.example.myproductsupplier.ui.auth.AuthViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setupAction()
    }

    private fun setupForm() : Boolean{
        var form = false

        val profileName = binding.edtProfileName.text.toString()
        val username = binding.edtUsername.text.toString()
        val password = binding.edtUsername.text.toString()

        when {
            profileName.isEmpty() -> {
                binding.tflProfileName.error = "Please fill your Profile Name."
            }
            username.isEmpty() -> {
                binding.tflUsername.error = "Please fill your Username."
            }
            password.isEmpty() -> {
                binding.tflPassword.error = "Please enter your Password."
            }
            password.length < 6 -> {
                binding.tflPassword.error = "Password length must be 6 character."
            }
            else -> form = true
        }
        return form
    }

    private fun initObserver() {
        viewModel.registerState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Login Successfully ${result.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, RegisterResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        "${errorResponse.status} ${errorResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            binding.apply {
                btnRegister.setOnClickListener {
                    if (setupForm()) {
                        viewModel.register(
                            profileName = edtProfileName.text.toString(),
                            username = edtUsername.text.toString(),
                            password = edtPassword.text.toString(),
                        )
                    }
                }
                binding.btnLogin.setOnClickListener {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}