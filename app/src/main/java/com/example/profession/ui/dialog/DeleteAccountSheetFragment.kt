package com.example.profession.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.nadifalaundries.R
import com.example.nadifalaundries.databinding.FragmentNewOrderBottomSheetBinding
import com.example.nadifalaundries.ui.activity.MainActivity
import com.example.nadifalaundries.ui.dialog.home_dialogs.OffLineDialog
import com.example.nadifalaundries.utils.Constants
import com.example.nadifalaundries.utils.ExpandAnimation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class DeleteAccountSheetFragment(var onClick: OffLineDialog.OnClick) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewOrderBottomSheetBinding
    private lateinit var parent: MainActivity

    companion object {
        fun newInstance(onClick: OffLineDialog.OnClick): DeleteAccountSheetFragment {
            val args = Bundle()
            val f = DeleteAccountSheetFragment(onClick)
            f.arguments = args
            return f
        }
    }

    var count = 0
    val timer = Timer()
    fun setupProgress() {

        binding.pb2.setProgress(count)
      var   mCountDownTimer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val date = Date(millisUntilFinished)
                val format = SimpleDateFormat("mm:ss")
                binding.pb2.progress=millisUntilFinished.toInt()

                binding.tvTime.text=   format.format(date)
                Log.v("Log_tag", "Tick of Progress$count$millisUntilFinished")
                count++
                binding.pb2.setProgress(count as Int * 100 / (180000 / 1000))

            }

            override fun onFinish() {
            dismiss()
                //Do what you want
               // count++
               // binding.pb2.setProgress(100)
            }
        }
        mCountDownTimer.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewOrderBottomSheetBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        setupProgress()
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.ivClose.setOnClickListener {
            timer.cancel()
            dismiss()
        }
        binding.ivUp.setOnClickListener {
            if (!binding.lytExpand.isVisible) {

                ExpandAnimation.expand(binding.lytExpand)
                binding.lytOrginalHeader.visibility = View.GONE

            }
        }
        binding.ivDawn.setOnClickListener {
            if (binding.lytExpand.isVisible) {
                ExpandAnimation.collapse(binding.lytExpand)
                binding.lytOrginalHeader.visibility = View.VISIBLE
            }

        }
        binding.btnOk.setOnClickListener {
            timer.cancel()
            dismiss()

            onClick.onClick(Constants.YES)
        }
        binding.btnReject.setOnClickListener {
            timer.cancel()
            dismiss()
            onClick.onClick(Constants.NO)

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