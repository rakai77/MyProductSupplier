package com.example.myproductsupplier.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproductsupplier.MainActivity
import com.example.myproductsupplier.R
import com.example.myproductsupplier.data.Resource
import com.example.myproductsupplier.data.remote.response.LoginResponse
import com.example.myproductsupplier.databinding.FragmentLoginBinding
import com.example.myproductsupplier.databinding.FragmentRegisterBinding
import com.example.myproductsupplier.ui.auth.AuthViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setupAction()

    }

    private fun setupForm() : Boolean{
        var form = false

        val username = binding.edtUsername.text.toString()
        val password = binding.edtUsername.text.toString()

        when {
            username.isEmpty() -> {
                binding.tflUsername.error = "Please fill your username."
            }
            password.isEmpty() -> {
                binding.tflPassword.error = "Please enter your password."
            }
            password.length < 6 -> {
                binding.tflPassword.error = "Password length must be 6 character."
            }
            else -> form = true
        }
        return form
    }

    private fun initObserver() {
        viewModel.loginState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Login Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    val errors = result.errorBody?.string()?.let { JSONObject(it).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(errors, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, LoginResponse::class.java)

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
            btnLogin.setOnClickListener {
                if (setupForm()) {
                    viewModel.login(
                        username = edtUsername.text.toString(),
                        password = edtPassword.text.toString(),
                    )
                }
            }
            btnSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}