package com.example.profession.ui.fragments.orderInfo


import androidx.core.view.isVisible
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