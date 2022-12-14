package com.example.profession.ui.fragments.providers

import android.content.Intent
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProviderProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.dialog.LoginFirstBotomSheetFragment
import com.example.profession.ui.dialog.OnClickLoginFirst
import com.example.profession.util.Constants
import com.example.profession.util.ExpandAnimation

class ProviderProfileFragment : BaseFragment<FragmentProviderProfileBinding>() {
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
    }

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
                    findNavController().navigate(R.id.reviewsFragment)
                }
             }


        }
        ).show(childFragmentManager, LoginFirstBotomSheetFragment::class.java.canonicalName)
    }
}