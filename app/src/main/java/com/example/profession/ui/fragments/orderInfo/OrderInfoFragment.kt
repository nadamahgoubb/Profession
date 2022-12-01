package com.example.profession.ui.fragments.orderInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderInfoBinding
import com.example.profession.ui.base.BaseFragment
import com.example.profession.util.ExpandAnimation.collapse

class OrderInfoFragment : BaseFragment<FragmentOrderInfoBinding>() {
    override fun onFragmentReady() {

        binding?.ivDawn?.setOnClickListener {
            collapse(binding?.cardPrice!!)
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        (arguments?.getString("ORDERID"))?.let {
            when(it){
                "1"->            binding.cardCompelted.isVisible=true

                "2"->           binding.cardInProgress.isVisible=true

                "4"->             binding.cardWaitingApproval.isVisible=true

            }
        }
    }

}