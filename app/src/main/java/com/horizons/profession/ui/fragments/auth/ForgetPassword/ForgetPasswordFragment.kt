package com.horizons.profession.ui.fragments.auth.ForgetPassword


import android.graphics.Paint
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentForgetPasswordBinding
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.ui.fragments.auth.AuthAction
import com.horizons.profession.ui.fragments.auth.AuthViewModel
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.observe
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneMultiFactorGenerator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>(),
    CountryCodePicker.OnCountryChangeListener {

    private val mViewModel: AuthViewModel by viewModels()

    var state = 1

    private var restTimer: CountDownTimer? = null
    private var countryCode: String = "+966"
    lateinit var phoneAuthProvider: PhoneAuthProvider.ForceResendingToken
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
     var otp = ""
    companion object{
        var verifiedOtp =""
    }
    override fun onFragmentReady() {
        state1()
        onClick()

        mViewModel.apply {
            observe(viewState) {
                handleViewState(it)
            }
        }
        onBack()

    }

    private fun onClick() {
        binding.btnResend.paintFlags = binding.btnResend.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.countryCodePicker.setOnCountryChangeListener(this)
        binding.btnNext.setOnClickListener {
            if (binding.etPhone.text.toString()
                    .isNullOrEmpty()
            ) showToast(resources.getString(R.string.please_enter_your_phone))
            else mViewModel.checkPhone(countryCode, binding.etPhone.text.toString())
        }
        binding.btnResend.setOnClickListener {
            if (binding.etPhone.text.toString()
                    .isNullOrEmpty()
            ) showToast(resources.getString(R.string.please_enter_your_phone))
            else mViewModel.checkPhone(countryCode, binding.etPhone.text.toString())
        }
        binding.btnSendOtp.setOnClickListener {
            if (binding.etOtp.otp.toString()
                    .isNullOrEmpty()
            ) showToast(resources.getString(R.string.msg_empty_otp))
            else {
                otp = binding.etOtp.otp.toString()
                 verfyOtp()

            }
        }
        binding.btnEnterPass.setOnClickListener {
            mViewModel.isValidParamsChangePass(
                countryCode,
                binding.etPassword.text.toString(),
                binding.etRepeatPassword.text.toString()
            )
        }
        binding.ivBack.setOnClickListener {
            when (state) {
                1 -> {


                    requireActivity().onBackPressed()
                }

                2 -> state1()
                3 -> state2()

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

            is AuthAction.ShowForgetPassword -> {
                showProgress(false)
                findNavController().navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                )
            }

            is AuthAction.ShowPhoneConfirmed -> {
                showProgress(false)
                if (action.data?.exists == 1) {
                    sendVerfaction(countryCode + mViewModel.phone)
                    state2()
                } else showToast(resources.getString(R.string.phone_not_exist))
            }

            is AuthAction.ShowFailureMsg -> action.message?.let {
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
                        when (state) {
                            1 -> {

                                if (isEnabled) {
                                    isEnabled = false
                                    requireActivity().onBackPressed()
                                }
                            }

                            2 -> state1()
                            3 -> state2()

                        }
                    }
                })


        }
    }

    private fun state2() {
        state = 2
        binding.lytFirst.isVisible = false
        binding.lytChangePass.isVisible = false
        binding.lytOtp.isVisible = true
        binding.tv1.text = resources.getString(R.string.enter_pin_code)
        binding.tv2.text = resources.getString(R.string.pincode_has_been_ent_to_your_phone)
        binding.tvcounter.isVisible = true
     }

    private fun state1() {
        state = 1
        binding.tvcounter.isVisible = false
        binding.lytFirst.isVisible = true
        binding.lytOtp.isVisible = false
        binding.lytChangePass.isVisible = false
        binding.tv1.text = resources.getString(R.string.change_password)
        binding.tv2.text = resources.getString(R.string.enter_your_emailto_get_code)
        binding.tvcounter.isVisible = false
    }

    private fun state3() {
        state = 3
        binding.tvcounter.isVisible = false
        binding.lytFirst.isVisible = false
        binding.lytOtp.isVisible = false
        binding.lytChangePass.isVisible = true

        binding.tv1.text = resources.getString(R.string.new_password_)
        binding.tv2.text = resources.getString(R.string.please_enter_your_pass)
    }

    private fun counterDawn() {
        binding.btnResend.isEnabled = false
        binding.btnResend.setTextColor(resources.getColor(R.color.grey_400))
        binding.tvcounter.setTextColor(resources.getColor(R.color.grey_400))

        restTimer = object : CountDownTimer(120000, 1000) {


            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000 % 60
                val minutes: Long = (millisUntilFinished - seconds) / 1000 / 60
                binding.tvcounter.text = "" + minutes + ":" + seconds
                Log.d(
                    "remainingremaining g", ("" + minutes + ":" + seconds).toString()
                )
            }


            override fun onFinish() {
                binding.btnResend.text = resources.getString(R.string.resend)
                binding.tvcounter.text = ""
                binding.btnResend.isEnabled = true
                binding.btnResend.setTextColor(resources.getColor(R.color.black))


            }
        }.start()
    }

    private fun sendVerfaction(phoneNumber: String) {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            }


            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(   p0.message.toString())
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                counterDawn()
                verifiedOtp = p0
                phoneAuthProvider = p1
            }
        }

        val options =
            PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verfyOtp() {
        var credential = PhoneAuthProvider.getCredential(verifiedOtp, otp)
        signin(credential)
        val multiFactorAssertion = PhoneMultiFactorGenerator.getAssertion(credential)

    }

    private fun signin(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                     Log.i("PhoneAuthCredential", "sucess")
                    state3()
                } else {
                    showToast(resources.getString(R.string.wrongotp))
                    Log.i("PhoneAuthCredential", "false")

                  }

        })

    }

    override fun onCountrySelected() {
        countryCode = "+" + binding.countryCodePicker.selectedCountryCode
    }
}