package com.example.profession.ui.fragments.auth.Register

import android.content.Intent
import android.graphics.Paint
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentRegisterBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.auth.AuthViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
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
        is    AuthAction.RegisterationSuccess -> {
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
        binding.btnSignIn.setPaintFlags(binding.btnSignIn.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        binding.terms.text =
            HtmlCompat.fromHtml(getString(R.string.some_text), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun onClick() {

        binding.btnRegister.setOnClickListener {
            mViewModel.isValidParams(binding.etUserName.text.toString(), binding.etPassword.text.toString())

        }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)

        }

    }
}