package com.horizons.profession.ui.fragments.contactUs


import android.text.Html
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentContactUsBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.ui.fragments.customerServie.SettingAction
import com.horizons.profession.ui.fragments.customerServie.SettingViewModel
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.showActivity
import com.horizons.profession.util.observe
import com.hbb20.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>(), CountryCodePicker.OnCountryChangeListener {

    private lateinit var parent: MainActivity
    val mviewmodel: SettingViewModel by viewModels()

    private var countryCode: String = "+966"
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
            binding.lytData.isVisible=true
            binding.tvGoals.setText(Html.fromHtml((action.data?.content)))
         }


        else -> {}
    }
}
        private fun onClick() {
        binding.ivBack.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
        binding.btnDone.setOnClickListener {
            if(binding.etPhone.text.toString().isEmpty()) showToast(resources.getString(R.string.please_enter_your_phone))
            if(binding.etMsg.text.toString().isEmpty()) showToast(resources.getString(R.string.msg_empty_content))
            else mviewmodel.contactUs(countryCode, binding.etPhone.text.toString(),binding.etMsg.text.toString())
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
    }
    override fun onCountrySelected() {
        countryCode = "+" + binding.countryCodePicker.selectedCountryCode
    }
}