package com.example.profession.ui.fragments.contactUs


import com.example.profession.databinding.FragmentContactUsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {

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

    }}