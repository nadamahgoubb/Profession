package com.example.profession.ui.fragments.walkThrough

import android.content.Intent
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.profession.R
import com.example.profession.databinding.FragmentWalkThrougthBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment

import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.start

@AndroidEntryPoint
class WalkThrougthFragment() : BaseFragment<FragmentWalkThrougthBinding>(), OnClickSkipListener {

    private var pos = 0
    override fun onFragmentReady() {
        setupViewPager()
        onClick()

    }


    private fun setupViewPager() {
    binding.btnNext.setText(R.string.next)
        val adapter = SectionsPagerAdapter(this,this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
            binding.viewPager.currentItem = 0
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                super.onPageSelected(position)
                pos = position

                when (pos) {
                    0 -> {
                        binding.btnNext.setText(R.string.next)


                    }
                    1 -> {
                        binding.btnNext.setText(R.string.start)

                    }

                }
            }
        })
    }

    private fun onClick() {
       binding.btnNext.setOnClickListener {
            if (pos == 0 ) {
                pos++
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
           else
                findNavController().navigate(R.id.loginFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())


        }


    }


    private fun goNext() {
        if (pos == 0 ) {
            pos++
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }

    private fun gotoLMain() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }

    override fun onSkipClickListener() {
        findNavController().navigate(R.id.loginFragment)
    }


}