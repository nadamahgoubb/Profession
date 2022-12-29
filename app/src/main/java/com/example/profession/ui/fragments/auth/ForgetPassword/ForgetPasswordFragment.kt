package com.example.profession.ui.fragments.auth.ForgetPassword


import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentForgetPasswordBinding
import com.example.profession.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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


        onBack()

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
    }

    private fun state1() {
      state=1
        binding.lytFirst.isVisible=true
        binding.lytOtp.isVisible = false
    binding.lytChangePass.isVisible=false
        binding.tv1.text=resources.getString(R.string.change_password)
        binding.tv2.text=resources.getString(R.string.enter_your_email)
    }

    private fun state3() {
    state=3
        binding.lytFirst.isVisible=false
        binding.lytOtp.isVisible = false
        binding.lytChangePass.isVisible=true

        binding.tv1.text=resources.getString(R.string.new_password_)
        binding.tv2.text=resources.getString(R.string.please_enter_your_pass)}
}