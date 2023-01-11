package com.example.profession.ui.fragments.customerServie

import androidx.fragment.app.viewModels
import com.example.profession.databinding.FragmentCustomerServiceBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.observe
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
                activity?.onBackPressed()
            }


            else -> {

            }
        }
    }

    private fun onClick() {
        binding.btnDone.setOnClickListener {
            mviewmodel.isValidParamsComplain(
                binding.etComplainTitle.toString(), binding.etComplainContent.toString()
            )
        }
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)

    }
}