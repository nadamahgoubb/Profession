package com.example.profession.ui.fragments.auth.login

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
 import com.hbb20.CountryCodePicker
 import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(),CountryCodePicker.OnCountryChangeListener {

    private val mViewModel: AuthViewModel by viewModels()
        private var countryCode: String = "+966"
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
        binding.tvForgetPass.paintFlags = binding.tvForgetPass.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.tvCreate.paintFlags = binding.tvCreate.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun onClick() {

        binding.tvCreate.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnSignIn.setOnClickListener {

         mViewModel.isValidParams(binding.etPhone.text.toString(), binding.etPassword.text.toString())
           // startActivity(Intent(activity, MainActivity::class.java))
       //     activity?.finish()

        }
        binding.tvForgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)

        }    }

    override fun onCountrySelected() {
        countryCode = "+" + binding.countryCodePicker.selectedCountryCode
    }

}