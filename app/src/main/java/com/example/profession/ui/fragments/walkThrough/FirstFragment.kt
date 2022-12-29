package com.example.profession.ui.fragments.walkThrough

import com.example.profession.R
import com.example.profession.databinding.ItemWalkthrougthBinding
import com.example.profession.base.BaseFragment
import com.example.profession.util.ext.loadImage

interface OnClickSkipListener {
    fun onSkipClickListener()
}

class FirstFragment(var state: Int,var listener :OnClickSkipListener) : BaseFragment<ItemWalkthrougthBinding>() {


    private fun setUpUi() {
        when (state) {
            0 -> {
                binding.tvTitle.text = resources.getString(R.string.services)
                binding.tvMsg.text = getString(R.string.alot_of_workers_are_available)
                binding.imgSlider.loadImage(R.drawable.walkthrought_0)


            }
            1 -> {
                binding.tvTitle.text = getString(R.string.with_simple_steps)
                binding.tvMsg.text = getString(R.string.you_can_have_aprovider)
                binding.imgSlider.loadImage(R.drawable.walkthrougth_1)

            }
        }

    }

    override fun onFragmentReady() {
        setUpUi()
        binding.tvSkip.setOnClickListener {
            listener.onSkipClickListener()
        }
    }
}