package com.example.profession.ui.fragments.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderBinding
import com.example.profession.ui.base.BaseFragment

class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    override fun onFragmentReady() {
        binding.item1.tvStatusCompelted.isVisible = true
        binding.item2.tvStatusInProgress.isVisible = true
        binding.item3.tvStatusInProgress.isVisible = true
        binding.item4.tvStatusMsgWaitingApproval.isVisible = true
        var bundle = Bundle()
        binding.item1.root.setOnClickListener {
            bundle.putString("ORDERID", "1")
            findNavController().navigate(R.id.orderInfoFragment,bundle)
        }
        binding.item2.root.setOnClickListener {
            bundle.putString("ORDERID", "2")
            findNavController().navigate(R.id.orderInfoFragment,bundle)
        }
        binding.item3.root.setOnClickListener {
            bundle.putString("ORDERID", "2")
            findNavController().navigate(R.id.orderInfoFragment,bundle)
        }
        binding.item4.root.setOnClickListener {
            bundle.putString("ORDERID", "4")
            findNavController().navigate(R.id.orderInfoFragment,bundle)
        }
        binding.item1.tvChat.setOnClickListener {
            findNavController().navigate(R.id.contactUsFragment)
        }
        binding.item1.ivChat.setOnClickListener {
            findNavController().navigate(R.id.contactUsFragment)
        }
    }

}