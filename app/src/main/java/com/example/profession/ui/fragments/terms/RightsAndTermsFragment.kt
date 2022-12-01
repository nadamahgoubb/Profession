package com.example.profession.ui.fragments.terms

import com.example.profession.databinding.FragmentRightsAndTermsBinding
import com.example.profession.ui.base.BaseFragment

class RightsAndTermsFragment : BaseFragment<FragmentRightsAndTermsBinding>() {
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}