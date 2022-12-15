package com.example.profession.ui.fragments.checkout

import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentCheckOutBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment


class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.orderSucessFragment)
        }
        setupUi()
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
    }
}