package com.example.profession.ui.fragments.auth.login

import android.content.Intent
import android.graphics.Paint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentLoginBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.auth.AuthViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val mViewModel: AuthViewModel by viewModels()

    override fun onFragmentReady() {
        onClick()
        setupUi()

        mViewModel.apply {
            observe(viewState) {
                handleViewState(it)
            }
        }
    }

    private fun handleViewState(action: AuthAction) {
        when (action) {
            is AuthAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
        is    AuthAction.LoginSuccess -> {
                showProgress(false)
                showActivity(MainActivity::class.java, clearAllStack = true)
            }

            is AuthAction.ShowFailureMsg ->
                action.message?.let {
                    showToast(action.message)
                    showProgress(false)

                }

            else -> {

            }
        }
    }

    private fun setupUi() {
        binding.tvForgetPass.setPaintFlags( binding.tvForgetPass.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        binding.tvCreate.setPaintFlags( binding.tvCreate.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)    }

    private fun onClick() {

        binding.tvCreate.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnSignIn.setOnClickListener {

         mViewModel.isValidParams(binding.etUserName.text.toString(), binding.etPassword.text.toString())
           // startActivity(Intent(activity, MainActivity::class.java))
       //     activity?.finish()

        }
        binding.tvForgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)

        }    }
}