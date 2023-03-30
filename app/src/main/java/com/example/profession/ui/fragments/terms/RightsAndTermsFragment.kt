package com.example.profession.ui.fragments.terms

import com.example.profession.databinding.FragmentRightsAndTermsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.util.ext.showActivity

class RightsAndTermsFragment : BaseFragment<FragmentRightsAndTermsBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
showActivity(MainActivity::class.java , clearAllStack = true)      }
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

