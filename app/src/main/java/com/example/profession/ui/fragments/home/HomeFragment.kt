package com.example.profession.ui.fragments.home


import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentHomeBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.OffersHomeAdapter
import com.example.profession.ui.adapter.ServicesHomeAdapter
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.listener.ServiceOnClickListener
import com.example.profession.util.ext.init
import com.google.android.material.appbar.AppBarLayout


class HomeFragment : BaseFragment<FragmentHomeBinding>(), ServiceOnClickListener {
    lateinit var adapterServices: ServicesHomeAdapter
    lateinit var adapterOffers: OffersHomeAdapter
    private lateinit var parent: MainActivity

    override fun onFragmentReady() {
        initAdapters()
        setupUi()
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) ==   binding.appBarLayout.getTotalScrollRange()) {
                // If collapsed, then do this
                binding.lytHeaderDetails.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.lytHeaderDetails.setVisibility(View.VISIBLE);
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
    }

    private fun initAdapters() {
        adapterServices = ServicesHomeAdapter(this, requireContext())
        binding.recServices.init(requireContext(), adapterServices, 3)

        adapterOffers = OffersHomeAdapter(this, requireContext())
        binding.rvOffersHome.init(requireContext(), adapterOffers, 1)
    }

    override fun onServiceClickListener() {

        findNavController().navigate(R.id.subServiceFragment)
    }

}