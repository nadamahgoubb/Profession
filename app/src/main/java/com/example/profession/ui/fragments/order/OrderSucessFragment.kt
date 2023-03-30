package com.example.profession.ui.fragments.order


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderSucessBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.util.Constants
import com.example.profession.util.ext.showActivity
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

        arguments?.getString(Constants.ORDERID)?.let {
            mViewModel.orderId=it
            binding.tvOrderId.setText(it)
        }
        binding.btnShowDetails.setOnClickListener {

            var bundle = Bundle()
            bundle.putString(Constants.ORDERID, Constants.New_ORDER)
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
            if (Math.abs(verticalOffset) ==   binding.appBarLayout.getTotalScrollRange()) {
                // If collapsed, then do this
                binding.tvTitle.setVisibility(View.GONE);
             } else if (verticalOffset == 0) {
                binding.tvTitle.setVisibility(View.VISIBLE);
             } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })

    }}

