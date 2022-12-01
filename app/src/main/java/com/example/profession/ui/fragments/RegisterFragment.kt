package com.example.profession.ui.fragments

import android.content.Intent
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentRegisterBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun onFragmentReady() {
        //  binding.terms.setText()
        //  (     R.string.by_signing_in_you_agreed_to_terms)?.let { binding.terms.setText(Html.fromHtml(it!!.toString()).toString()); }
        binding.terms.text =
            HtmlCompat.fromHtml(getString(R.string.some_text), HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
            }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)

        }
    }
}