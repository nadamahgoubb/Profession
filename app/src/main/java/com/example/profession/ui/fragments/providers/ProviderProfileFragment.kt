package com.example.profession.ui.fragments.providers

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProviderProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.dialog.LoginFirstBotomSheetFragment
import com.example.profession.ui.dialog.OnClickLoginFirst
import com.example.profession.util.Constants
import com.example.profession.util.ExpandAnimation
import com.google.android.material.appbar.AppBarLayout

class ProviderProfileFragment : BaseFragment<FragmentProviderProfileBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    binding.ivDawn.setOnClickListener {
        ExpandAnimation.expand(binding.cardUp)
        ExpandAnimation.collapse(binding.cardDawn)
    }
        binding.ivUp.setOnClickListener {
            ExpandAnimation.expand(binding.cardDawn)
            ExpandAnimation.collapse(binding.cardUp)
        }
        binding.btnOrder.setOnClickListener {
            showLoginFirstBotomSheetFragment()
        }
        binding.lytRate.setOnClickListener {
            findNavController().navigate(R.id.reviewsFragment)
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
                binding.lytImg.setVisibility(View.GONE);
                binding.ivProfile.setVisibility(View.GONE);
                binding.ivCall.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.lytImg.setVisibility(View.VISIBLE);
                binding.ivProfile.setVisibility(View.VISIBLE);
                binding.ivCall.setVisibility(View.VISIBLE);
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })  }


    private fun showLoginFirstBotomSheetFragment() {
        LoginFirstBotomSheetFragment.newInstance(object : OnClickLoginFirst {
            override fun onClick(choice: String) {
                if(choice.equals(Constants.YES)){
                    var intent = Intent(activity, AuthActivity::class.java)
                    intent.putExtra(Constants.Start, Constants.login)
                    startActivity(intent)
                    activity?.finish()
                }
                else{
                    findNavController().navigate(R.id.chooseTimeFragment)
                }
             }


        }
        ).show(childFragmentManager, LoginFirstBotomSheetFragment::class.java.canonicalName)
    }
}