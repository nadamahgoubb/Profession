package com.example.profession.ui.fragments.terms

import android.text.Html
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.profession.databinding.FragmentRightsAndTermsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.fragments.customerServie.SettingAction
import com.example.profession.ui.fragments.customerServie.SettingViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RightsAndTermsFragment : BaseFragment<FragmentRightsAndTermsBinding>() {
    private lateinit var parent: MainActivity
    val mviewmodel: SettingViewModel by viewModels()
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
       //     showActivity(MainActivity::class.java, clearAllStack = true)
        }
        setupUi()

        mviewmodel.apply {

            observe(viewState) {
                handleViewState(it)
            }

        }
    //    onBack()
    }
    private fun onBack() {
        activity?.let {
            requireActivity().onBackPressedDispatcher.addCallback(it,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        if (isEnabled) {
                            isEnabled = false
                            showActivity(MainActivity::class.java, clearAllStack = true)
                        }

                    }
                })
        }
    }

    private fun handleViewState(action: SettingAction) {
        when (action) {
            is SettingAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }

            is SettingAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            is SettingAction.ShowTermsAndConditions -> {
                showProgress(false)
                 binding.tvTerms.setText(Html.fromHtml((action.data?.content)))
            }


            else -> {

            }
        }
    }

    private fun setupUi() {
        try {
            parent = requireActivity() as MainActivity
            binding.ivMenu.isVisible = true
            parent.showBottomNav(false)
            parent.showSideNav(true)
            binding.ivMenu.setOnClickListener {
                parent.openDrawer()
            }
        } catch (e: Exception) {
            binding.ivMenu.isVisible = false
        }
    }

}

