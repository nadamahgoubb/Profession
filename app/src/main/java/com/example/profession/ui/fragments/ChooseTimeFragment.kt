package com.example.profession.ui.fragments


import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentChooseTimeBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.ChooseTimeAdapter
import com.example.profession.base.BaseFragment
import com.example.profession.ui.listener.ChooseTimeOnClickListener
import com.example.profession.util.ext.init
import com.google.android.material.appbar.AppBarLayout

class ChooseTimeFragment : BaseFragment<FragmentChooseTimeBinding>(), ChooseTimeOnClickListener {
    private lateinit var parent: MainActivity
    lateinit var adapter: ChooseTimeAdapter
    override fun onFragmentReady() {
        initAdapters()
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
                binding.tvTitleHeader.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.tvTitleHeader.setVisibility(View.VISIBLE);
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
    }
    private fun initAdapters() {
        adapter = ChooseTimeAdapter(this)
        binding.rvAvailableTime.init(requireContext(), adapter, 3)
    }

    override fun onChooseTimeClickListener() {
        findNavController().navigate(R.id.checkOutFragment)
    }
}