package com.example.profession.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.databinding.FragmentChangePasswordBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.util.Constants
import com.example.profession.util.Extension
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {


    private val mViewModel: ProfileViewModel by viewModels()

    //  private lateinit var parent: MainActivity
    override fun onFragmentReady() {
        onclick()
        mViewModel.apply {
            observe(viewState) {
                handleViewState(it)
            }
        }
    }

    private fun request() {
        mViewModel.isValidParamsChangePass(
            binding.etPassword.text.toString(),
            binding.etNewPassword.text.toString(),
            binding.etRpeatNewPassword.text.toString()
        )
    }


    private fun handleViewState(action: ProfileAction) {
        when (action) {
            is ProfileAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is ProfileAction.ShowFailureMsg -> action.message?.let {
                // if (it.contains("401")) {
                //   activity?.let { it1 -> Extension.logoutAnAuth(it1) }
                //  } else {
                showToast(action.message)
                showProgress(false)
                //   }
            }
            is ProfileAction.ChangedPassword -> {
                showToast(action.message)
                PrefsHelper.clear()

                var intent = Intent(activity, AuthActivity::class.java)
                intent.putExtra(Constants.Start, Constants.login)
                startActivity(intent)
                activity?.finish()
            }


            else -> {}
        }
    }


    private fun onclick() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()

        }
        binding.btnSave.setOnClickListener {
            request()
            //
        }
    }
}