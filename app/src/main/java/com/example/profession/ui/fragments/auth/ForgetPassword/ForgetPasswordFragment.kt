package com.example.profession.ui.fragments.auth.ForgetPassword


import android.graphics.Paint
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentForgetPasswordBinding
import com.example.profession.base.BaseFragment
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.auth.AuthViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {
   var state= 1
    private val mViewModel: AuthViewModel by viewModels()
    override fun onFragmentReady() {
        binding.btnResend.setPaintFlags(binding.btnResend.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        state1()
        binding.btnNext.setOnClickListener {
            state2()
        }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnResend.setOnClickListener {
            //    state2()
        }
        binding.btnSendOtp.setOnClickListener {
            state3()
        }
binding.ivBack.setOnClickListener {
    when(state) {
        1 -> {


            requireActivity().onBackPressed()
        }

        2 -> state1()
        3 -> state2()

    }
}
        mViewModel.apply {
            observe(viewState) {
                handleViewState(it)
            }
        }
        onBack()

    }
    private fun handleViewState(action: AuthAction) {
        when (action) {
            is AuthAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is    AuthAction.ShowForgetPassword -> {
                showProgress(false)
                findNavController().navigate(R.id.loginFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
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

    private fun onBack() {
        activity?.let {
            requireActivity().onBackPressedDispatcher.addCallback(
                it,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        when(state){
                            1-> {

                                if (isEnabled) {
                                    isEnabled = false
                                    requireActivity().onBackPressed()
                                }
                            }
                            2-> state1()
                            3->state2()

                        }
                    }
                })


        }    }

    private fun state2() {
    state= 2
      binding.lytFirst.isVisible=false
        binding.lytChangePass.isVisible=false
        binding.lytOtp.isVisible = true
        binding.tv1.text=resources.getString(R.string.enter_pin_code)
        binding.tv2.text=resources.getString(R.string.pincode_has_been_ent_to_your_phone)

   binding.tvcounter.isVisible= true }

    private fun state1() {
      state=1
        binding.tvcounter.isVisible= false
        binding.lytFirst.isVisible=true
        binding.lytOtp.isVisible = false
    binding.lytChangePass.isVisible=false
        binding.tv1.text=resources.getString(R.string.change_password)
        binding.tv2.text=resources.getString(R.string.enter_your_email)
  binding.tvcounter.isVisible= false }

    private fun state3() {
    state=3
        binding.tvcounter.isVisible= false
        binding.lytFirst.isVisible=false
        binding.lytOtp.isVisible = false
        binding.lytChangePass.isVisible=true

        binding.tv1.text=resources.getString(R.string.new_password_)
        binding.tv2.text=resources.getString(R.string.please_enter_your_pass)}
}