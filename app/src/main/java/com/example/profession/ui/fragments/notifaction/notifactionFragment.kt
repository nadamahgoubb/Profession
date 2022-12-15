package com.example.profession.ui.fragments.notifaction

import com.example.profession.databinding.FragmentNotifactionBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment

class notifactionFragment : BaseFragment<FragmentNotifactionBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupUi()
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }  }


}