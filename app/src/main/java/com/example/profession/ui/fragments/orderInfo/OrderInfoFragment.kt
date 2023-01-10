package com.example.profession.ui.fragments.orderInfo


import androidx.core.view.isVisible
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderInfoBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.dialog.ComplainSheetFragment
import com.example.profession.ui.dialog.DeleteAccountSheetFragment
import com.example.profession.ui.dialog.OnClick
import com.example.profession.ui.dialog.OnClickComplain
import com.example.profession.util.Constants
import com.example.profession.util.ExpandAnimation.collapse

class OrderInfoFragment : BaseFragment<FragmentOrderInfoBinding>() {
    private lateinit var parent: MainActivity

    var status = "-1"
    override fun onFragmentReady() {

        setupUi()
        (arguments?.getString("ORDERID"))?.let {
            status=it
            when(it){
                "1"-> {
                    binding.cardCompelted.isVisible = true
                    binding.btnDone.setText(R.string.complain)  }

                "2"-> {
                    binding.cardInProgress.isVisible = true
                    binding.btnDone.setText(R.string.complain)
                }

                "4"-> {
                    binding.cardWaitingApproval.isVisible = true
                    binding.btnDone.setText(R.string.cancel)

                }

            }
        }

        binding?.ivDawn?.setOnClickListener {
            collapse(binding?.cardPrice!!)
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnDone.setOnClickListener {
            if(it.equals("1")) showDeletBotttomSheetFragment()        }
    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)


    }
    private fun showDeletBotttomSheetFragment() {
        ComplainSheetFragment.newInstance(object : OnClickComplain {
            override fun onClick(choice: String) {
                if (choice.equals(Constants.YES)) {


                } else {

                }
            }


        }).show(childFragmentManager, ComplainSheetFragment::class.java.canonicalName)
    }
}