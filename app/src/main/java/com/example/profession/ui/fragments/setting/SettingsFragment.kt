package com.example.profession.ui.fragments.setting


import com.example.profession.databinding.FragmentSettingsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        setupUi()

    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}