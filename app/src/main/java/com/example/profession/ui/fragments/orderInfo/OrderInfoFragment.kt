package com.example.profession.ui.fragments.orderInfo


import androidx.core.view.isVisible
 import com.example.profession.databinding.FragmentOrderInfoBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
 import com.example.profession.util.ExpandAnimation.collapse

class OrderInfoFragment : BaseFragment<FragmentOrderInfoBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {

        binding?.ivDawn?.setOnClickListener {
            collapse(binding?.cardPrice!!)
         }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupUi()
        (arguments?.getString("ORDERID"))?.let {
            when(it){
                "1"->            binding.cardCompelted.isVisible=true

                "2"->           binding.cardInProgress.isVisible=true

                "4"->             binding.cardWaitingApproval.isVisible=true

            }
        }
    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }

    }
}