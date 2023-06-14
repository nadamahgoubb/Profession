package com.example.profession.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.profession.R
import com.example.profession.databinding.FragmentDeleteAccountSheetBinding
import com.example.profession.databinding.FragmentLoginBinding
import com.example.profession.databinding.FragmentLoginFirstSheetBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

interface OnClickLoginFirst{
    fun onClick(choice: String)
}

class LoginFirstBotomSheetFragment( ) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLoginFirstSheetBinding
    private lateinit var parent: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginFirstSheetBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.btnNo.setOnClickListener {
             dismiss()
        }
        binding.ivDawn.setOnClickListener {
             dismiss()
        }
        binding.btnOk.setOnClickListener {
             var intent = Intent(activity, AuthActivity::class.java)
            intent.putExtra(Constants.Start, Constants.login)
            startActivity(intent)
            activity?.finish()
            dismiss()
        }
    }

    @SuppressLint("CutPasteId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme

}