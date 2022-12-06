package com.example.profession.ui.fragments.subService


import android.view.View
import androidx.core.view.isVisible
import com.example.profession.databinding.FragmentSubServiceBinding
import com.example.profession.ui.base.BaseFragment
import com.example.profession.util.ExpandAnimation

class SubServiceFragment : BaseFragment<FragmentSubServiceBinding>() {
    override fun onFragmentReady() {
        setupBottomCard()
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupBottomCard() {
        binding.ivUp.setOnClickListener {
            up()
        }
        binding.ivDawn.setOnClickListener {
            if (!binding.lytCustomerService.isVisible) up()
            else dawn()

        }
        binding.tvUp.setOnClickListener {
            up()

        }
    }

    fun up() {
        if (!binding.lytCustomerService.isVisible) {

            ExpandAnimation.expand(binding.lytCustomerService)
            binding.ivUp.visibility = View.GONE
            binding.tvUp.visibility = View.GONE
binding.ivDawn.rotation=90F
        }
    }

    fun dawn() {
        if (binding.lytCustomerService.isVisible) {

            ExpandAnimation.collapse(binding.lytCustomerService)
            binding.ivUp.visibility = View.VISIBLE
            binding.tvUp.visibility = View.VISIBLE
            binding.ivDawn.rotation=270F

        }
    }
}