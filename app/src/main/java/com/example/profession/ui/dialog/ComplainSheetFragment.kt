package com.example.profession.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.profession.R
import com.example.profession.databinding.DialogComplainBottomsheetBinding
import com.example.profession.databinding.FragmentDeleteAccountSheetBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

interface OnClickComplain{
    fun onClick(comment: String)
}

class ComplainSheetFragment(var onClick: OnClickComplain) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogComplainBottomsheetBinding
    private lateinit var parent: MainActivity

    companion object {
        fun newInstance(onClick:OnClickComplain): ComplainSheetFragment {
            val args = Bundle()
            val f = ComplainSheetFragment(onClick)
            f.arguments = args
            return f
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       binding = DialogComplainBottomsheetBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()
        return binding.root
    }

    private fun onClick() {

        binding.btnOk.setOnClickListener {
            if(binding.etComplain.text.toString().isNullOrEmpty()) Toast.makeText(requireContext(), getText(R.string.empty_comment), Toast.LENGTH_SHORT).show()
            else {
                onClick.onClick(binding.etComplain.text.toString() )
                dismiss()
            }
        }
        binding.ivDawn.setOnClickListener {
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