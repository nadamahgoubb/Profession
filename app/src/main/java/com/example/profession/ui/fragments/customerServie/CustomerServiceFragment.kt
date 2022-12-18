package com.example.profession.ui.fragments.customerServie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.profession.R
import com.example.profession.databinding.FragmentCustomerServiceBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import org.jsoup.Connection.Base

class CustomerServiceFragment : BaseFragment<FragmentCustomerServiceBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        setupUi()
    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}