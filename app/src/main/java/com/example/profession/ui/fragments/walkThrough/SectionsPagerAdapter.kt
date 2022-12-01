package com.example.profession.ui.fragments.walkThrough

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(fragmentActivity: WalkThrougthFragment) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FirstFragment(0)
            1 -> return FirstFragment(1)
        }
        return FirstFragment(-1)
    }
}