package com.example.profession.ui.fragments.auth


import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentForgetPasswordBinding
import com.example.profession.ui.base.BaseFragment

class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {
   var state= 1
    override fun onFragmentReady() {
    //    showProgress(true)
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

    activity?.let {
        requireActivity().onBackPressedDispatcher.addCallback(
            it,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                   when(state){
                       1-> activity?.onBackPressed()
                          2-> state1()
                       3->state2()

                   }
                }
            })


    }
    }

    private fun state2() {
    state= 2
      binding.lytFirst.isVisible=false
        binding.lytOtp.isVisible = true
    }

    private fun state1() {
      state=1
        binding.lytFirst.isVisible=true
        binding.lytOtp.isVisible = false
    binding.lytPass.isVisible=false}

    private fun state3() {
    state=3
        binding.lytFirst.isVisible=false
        binding.lytOtp.isVisible = false
        binding.lytChangePass.isVisible=true    }
}