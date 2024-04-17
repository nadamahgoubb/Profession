package com.horizons.profession.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.horizons.profession.R
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.databinding.DialogRightsAndTermsBinding
import com.horizons.profession.ui.fragments.customerServie.SettingAction
import com.horizons.profession.ui.fragments.customerServie.SettingViewModel
import com.horizons.profession.util.ToastUtils.Companion.showToast
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.showActivity
import com.horizons.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RightsAndTermDialog : DialogFragment(R.layout.dialog_rights_and_terms) {

    private lateinit var binding: DialogRightsAndTermsBinding
     val mviewmodel: SettingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (dialog?.isShowing == false) {
            binding = DialogRightsAndTermsBinding.inflate(inflater)
            return binding.root
        } else return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog!!.window?.setLayout(width, height)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window?.setGravity(Gravity.CENTER)





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
            requireActivity().onBackPressedDispatcher.addCallback(
                it,
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
                showToast(requireContext(), action.message)
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
binding.ivBack.setOnClickListener {
    dismiss()
}
    }

    fun showProgress(boolean: Boolean) {
    binding.progressBar.isVisible=boolean
        showToast(requireContext(),boolean.toString())
    }
}

