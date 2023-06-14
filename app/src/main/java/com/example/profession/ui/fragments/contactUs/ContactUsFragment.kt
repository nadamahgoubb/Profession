package com.example.profession.ui.fragments.contactUs


import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.profession.R
import com.example.profession.databinding.FragmentContactUsBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.ui.fragments.customerServie.SettingAction
import com.example.profession.ui.fragments.customerServie.SettingViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {

    private lateinit var parent: MainActivity
    val mviewmodel: SettingViewModel by viewModels()

    override fun onFragmentReady() {

        setupUi()
        onClick()

    mviewmodel.apply {
mviewmodel.get_goal()
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

        is SettingAction.ContactSucessed -> {
            showProgress(false)
            showToast(action.message)
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
        is SettingAction.ShowGoal -> {
            showProgress(false)
           binding.tvGoals.setText(action.data?.content)
        }


        else -> {}
    }
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