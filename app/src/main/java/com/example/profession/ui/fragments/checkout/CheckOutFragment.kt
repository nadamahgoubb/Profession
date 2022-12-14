package com.example.profession.ui.fragments.checkout

  import androidx.navigation.fragment.findNavController
 import com.example.profession.R
 import com.example.profession.databinding.FragmentCheckOutBinding
 import com.example.profession.ui.base.BaseFragment


class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>() {
    override fun onFragmentReady() {
binding.btnDone.setOnClickListener {
    findNavController().navigate(R.id.orderSucessFragment)
}
    }

}