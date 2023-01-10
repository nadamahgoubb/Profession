package com.example.profession.ui.fragments.providers


import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProvidersBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.dialog.FilterBottomSheet
import com.example.profession.ui.dialog.OnFilterClick


class ProvidersFragment : BaseFragment<FragmentProvidersBinding>() {
    private lateinit var parent: MainActivity

    override fun onFragmentReady() {
        onclick()
        setupUi()
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
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
        binding.ivFilter.setOnClickListener {
            showFilterBottomSheet()
        }
    }

    fun showFilterBottomSheet() {
        FilterBottomSheet.newInstance(object : OnFilterClick {
            override fun onFilterSubmitted(brandId: ArrayList<Int>?, sortId: String?) {

            }


        })
            .show(childFragmentManager, FilterBottomSheet::class.java.canonicalName)

    }
}