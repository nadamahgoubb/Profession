package com.horizons.profession.ui.fragments.customerServie

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.horizons.profession.databinding.FragmentCustomerServiceBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.showActivity
import com.horizons.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerServiceFragment : BaseFragment<FragmentCustomerServiceBinding>() {
    private lateinit var parent: MainActivity
    val mviewmodel: SettingViewModel by viewModels()
    override fun onFragmentReady() {
        setupUi()
        onClick()
        mviewmodel.apply {

            observe(viewState) {
                handleViewState(it)
            }

        }
        onBack()
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

            is SettingAction.CompalinSucessed -> {
                showProgress(false)
                showToast(action.message)
                showActivity(MainActivity::class.java, clearAllStack = true)
            }


            else -> {

            }
        }
    }

    private fun onClick() {
        binding.btnDone.setOnClickListener {
            mviewmodel.isValidParamsComplain(
                binding.etComplainTitle.text.toString(), binding.etComplainContent.text.toString()
            )
        }
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.ivBack.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)

    }
}