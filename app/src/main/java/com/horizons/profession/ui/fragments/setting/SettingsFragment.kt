package com.horizons.profession.ui.fragments.setting


import androidx.activity.OnBackPressedCallback
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentSettingsBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import com.horizons.profession.util.Constants
import com.horizons.profession.util.ext.showActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private lateinit var parent: MainActivity
    var current=""
    override fun onFragmentReady() {
        setupUi()

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
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.ivBack.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
        current =PrefsHelper.getLanguage()
        if(current.equals(Constants.EN)) binding.btnLang.text = resources.getString(R.string.arabic)
        else binding.btnLang.text = resources.getString(R.string.english)

        binding.btnLang.setOnClickListener {
            if(current.equals(Constants.EN))
                PrefsHelper.setLanguage(Constants.AR)
            else   PrefsHelper.setLanguage(Constants.EN)
showActivity(MainActivity::class.java, clearAllStack = true)
        }
    }

}