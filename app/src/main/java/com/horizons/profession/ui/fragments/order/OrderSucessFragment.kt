package com.horizons.profession.ui.fragments.order


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentOrderSucessBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.util.Constants
import com.horizons.profession.util.ext.showActivity
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSucessFragment : BaseFragment<FragmentOrderSucessBinding>() {
    private lateinit var parent: MainActivity
      val mViewModel: OrdersViewModel by activityViewModels()
    override fun onFragmentReady() {
binding.btnContinue.setOnClickListener {
    showActivity(MainActivity::class.java, clearAllStack = true)

}

        arguments?.getString(Constants.STATUS)?.let {
            mViewModel.orderId=it
            binding.tvOrderId.text = it
        }
        binding.btnShowDetails.setOnClickListener {

            var bundle = Bundle()
            bundle.putString(Constants.STATUS, Constants.New_ORDER)
             findNavController().navigate(
                R.id.orderInfoFragment,
                bundle,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            )
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupUi()

    }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) ==   binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.tvTitle.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.tvTitle.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })

    }}

