package com.example.profession.ui.fragments.providers


import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProvidersBinding
import com.example.profession.ui.base.BaseFragment


class ProvidersFragment : BaseFragment<FragmentProvidersBinding>() {
    override fun onFragmentReady() {

        onclick()
     }

    private fun onclick() {
         binding.item1.root.setOnClickListener {

             findNavController().navigate(R.id.providerProfileFragment)
         }
        binding.item2.root.setOnClickListener {

            findNavController().navigate(R.id.providerProfileFragment)
        }
        binding.item3.root.setOnClickListener {

            findNavController().navigate(R.id.providerProfileFragment)
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}