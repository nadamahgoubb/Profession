package com.example.profession.ui.fragments.terms

import com.example.profession.databinding.FragmentRightsAndTermsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment

class RightsAndTermsFragment : BaseFragment<FragmentRightsAndTermsBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupUi()

    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
    }

}

