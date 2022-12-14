package com.example.profession.ui.fragments.order


import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderSucessBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.util.ext.showActivity


class OrderSucessFragment : BaseFragment<FragmentOrderSucessBinding>() {
    override fun onFragmentReady() {
binding.btnContinue.setOnClickListener {
    showActivity(MainActivity::class.java, clearAllStack = true)

}
        binding.btnShowDetails.setOnClickListener {
          var bundle =Bundle()
            bundle.putString("ORDERID", "4")
            findNavController().navigate(R.id.orderInfoFragment,bundle)
        }
    }

}