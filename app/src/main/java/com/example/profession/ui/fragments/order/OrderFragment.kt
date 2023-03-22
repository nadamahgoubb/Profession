package com.example.profession.ui.fragments.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profession.databinding.FragmentOrderBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.util.Constants.CURRENT_ORDER
import com.example.profession.util.Constants.New_ORDER
import com.example.profession.util.Constants.PREV_ORDER
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    private lateinit var parent: MainActivity

    private lateinit var mSectionAdapter: SectionsPagerAdapterTabs

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }

    }


    private fun initViews() {
         mSectionAdapter = SectionsPagerAdapterTabs(getChildFragmentManager())
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
        binding.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager))
        binding.viewPager.setAdapter(mSectionAdapter)

    }

    override fun onFragmentReady() {
        setupUi()
        initViews()
        onclick()
    }

    private fun onclick() {

    }


    private class SectionsPagerAdapterTabs(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                 OrderItemFragment(PREV_ORDER)
            }
            else if (position == 1) OrderItemFragment(CURRENT_ORDER)
            else OrderItemFragment(New_ORDER)

        }

        override fun getCount(): Int {
            return 3
        }
    }
}