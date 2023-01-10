package com.example.profession.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.profession.R
import com.example.profession.databinding.DialogBootomFilterOrdersBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

interface OnFilterClick {
    fun onFilterSubmitted(brandId: ArrayList<Int>?, sortId: String?)
}

@AndroidEntryPoint
class FilterBottomSheet(
var onClick: OnFilterClick
) : BottomSheetDialogFragment() {

    lateinit var binding: DialogBootomFilterOrdersBinding


    companion object {
        fun newInstance(onClick: OnFilterClick
        ): FilterBottomSheet {
            val args = Bundle()
            val f = FilterBottomSheet(onClick
            )
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBootomFilterOrdersBinding.inflate(inflater)
setupUi()
        binding.btnDone.setOnClickListener {

       onClick.onFilterSubmitted(null,null)
            dismiss()
        }
        binding.ivDawn.setOnClickListener {
            onClick.onFilterSubmitted(null,null)
            dismiss()
        }
        binding.tvDelete.setOnClickListener {
         }

        return binding.root
    }

    private fun setupUi() {
        binding.lytHighToLaw.tvName.text = resources.getString(R.string.high_to_law)
        binding.lytLowTohight.tvName.text = resources.getString(R.string.law_to_high)


    }
}