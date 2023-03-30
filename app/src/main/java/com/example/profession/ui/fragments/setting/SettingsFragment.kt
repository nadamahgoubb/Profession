package com.example.profession.ui.fragments.setting


import com.example.profession.R
import com.example.profession.databinding.FragmentSettingsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.util.Constants
import com.example.profession.util.ext.showActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private lateinit var parent: MainActivity
    var current=""
    override fun onFragmentReady() {
        setupUi()

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
        if(current.equals(Constants.EN)) binding.btnLang.setText(resources.getString(R.string.arabic))
        else  binding.btnLang.setText(resources.getString(R.string.english))

        binding.btnLang.setOnClickListener {
            if(current.equals(Constants.EN))
                PrefsHelper.setLanguage(Constants.AR)
            else   PrefsHelper.setLanguage(Constants.EN)
showActivity(MainActivity::class.java, clearAllStack = true)
        }
    }

}