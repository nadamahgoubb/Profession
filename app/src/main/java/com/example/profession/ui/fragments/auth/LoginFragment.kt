package com.example.profession.ui.fragments.auth

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentLoginBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun onFragmentReady() {
        binding.tvCreate.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()

        }
        binding.tvForgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)

        }
    }
}