package com.example.profession.ui.fragments.contactUs


import com.example.profession.databinding.FragmentContactUsBinding
import com.example.profession.ui.base.BaseFragment

class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {


    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}