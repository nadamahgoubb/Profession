package com.example.profession.ui.fragments.contactUs


import androidx.fragment.app.viewModels
import com.example.profession.R
import com.example.profession.databinding.FragmentContactUsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.fragments.customerServie.SettingViewModel
import com.example.profession.util.ext.showActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {

    private lateinit var parent: MainActivity
    val mviewmodel: SettingViewModel by viewModels()

    override fun onFragmentReady() {

        setupUi()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
        binding.btnDone.setOnClickListener {
            if(binding.etMsg.text.toString().isEmpty()) showToast(resources.getString(R.string.msg_empty_content))
            else mviewmodel.contactUs(binding.etMsg.text.toString())
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }

    }}