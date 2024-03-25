package com.example.profession.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible

import com.example.profession.R
import com.example.profession.data.dataSource.response.NationalitiesItem
import com.example.profession.databinding.DialogBootomFilterOrdersBinding
import com.example.profession.util.Constants
import com.example.profession.util.SpinnerHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_bootom_filter_orders.view.*
import kotlin.math.roundToInt

interface OnFilterClick {
    fun onFilterSubmitted(sort: Int?, nationality: Int?, rates: Double?, distance: Double?,)
}

@AndroidEntryPoint
class FilterBottomSheet(
    var onClick: OnFilterClick, var nationalities: ArrayList<NationalitiesItem>?, var nationalitiesId: Int?,
    var  sort: Int?,
    var  rate: Double?,
    var distance: Double?
) : BottomSheetDialogFragment() {

    lateinit var binding: DialogBootomFilterOrdersBinding
/*    var nationalitiesId : Int? = null
    var sort: Int? = null
    var rate: Float? = null
     var distance: Double? = null*/

    companion object {
        fun newInstance(
            onClick: OnFilterClick,
            nationalities: ArrayList<NationalitiesItem>?,
             nationalitiesId: Int?,
          sort: Int?,
          rate: Double?,
          distance: Double?
        ): FilterBottomSheet {
            val args = Bundle()
            val f = FilterBottomSheet(
                onClick, nationalities, nationalitiesId ,
                sort,
                rate,
                distance
            )
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogBootomFilterOrdersBinding.inflate(inflater)
        setupUi()
        setupPreData()
        binding.btnDone.setOnClickListener {
if(nationalitiesId==-1) nationalitiesId= null
            onClick.onFilterSubmitted(sort, nationalitiesId, rate?.toDouble(), distance)
            dismiss()
        }
        binding.ivDawn.setOnClickListener {
             dismiss()
        }
        binding.tvDelete.setOnClickListener {

            sort = null
            binding.lytHighToLaw.checkbox.isChecked = false
            binding.lytLowTohigh.checkbox.isChecked = false
            nationalitiesId = null
            binding.spinnerDistrict.setSelection(-1)

            binding.rating.rating = 0F
            binding.pbDestantion.progress = 0
            rate= null
            binding.tvPos.isVisible= false
distance=null

        }
        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rate = rating.toDouble()
                Toast.makeText(requireContext(), rating.toString(), Toast.LENGTH_SHORT).show()
            }

        binding.pbDestantion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
binding.tvPos.isVisible= true
                val value = seekBar?.progress
                distance= value?.toDouble()
                binding.tvPos.text = "$value "+resources.getString(R.string.km)
                if (value != 0) {

                    val cur = seekBar!!.width / seekBar.max

                  binding.tvPos.x = (cur * value!!).toFloat() + 10
                  //  binding.tvPos.x = (  value!!).toFloat() + 10
                }
              //  binding.tvPos.y = seekBar?.pivotY!! + 10
                Log.d("Pos", binding.tvPos.x.toString() + ": " + seekBar.width + ":" + seekBar.x)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
             }

            override fun onStopTrackingTouch(p0: SeekBar?) {
             }
        })

        return binding.root
    }

    private fun setupPreData() {
        sort ?.let {
            when(it) {
                Constants.HighToLaw ->{
                    checkOneSort(binding.lytHighToLaw.checkbox, binding.lytLowTohigh.checkbox,Constants.HighToLaw )
                }
                Constants.LowTohigh ->{
                    checkOneSort(binding.lytLowTohigh.checkbox, binding.lytHighToLaw.checkbox,Constants.LowTohigh )

                }

            }
        }

         
    //    binding.spinnerDistrict.setSelection(nationalitiesId.get)

    rate?.let {
        binding.rating.progress = it.roundToInt()

     }
        distance?.let {
        binding.pbDestantion.progress = it.toInt()
    }
        

    }

    private fun setupUi() {
        binding.lytHighToLaw.tvName.text = resources.getString(R.string.high_to_law)
        binding.lytLowTohigh.tvName.text = resources.getString(R.string.law_to_high)
        binding.lytHighToLaw.root.setOnClickListener {

            checkOneSort(   binding.lytHighToLaw.checkbox,    binding.lytLowTohigh.checkbox,Constants.HighToLaw)
        }
        binding.lytLowTohigh.root.setOnClickListener {
          /*  binding.lytHighToLaw.checkbox.isChecked = false
            binding.lytLowTohigh.checkbox.isChecked = true
            sort = Constants.LowTohigh*/
            checkOneSort(   binding.lytLowTohigh.checkbox,    binding.lytHighToLaw.checkbox,Constants.LowTohigh)

        }


        binding.lytHighToLaw.checkbox.setOnClickListener {
            if (binding.lytHighToLaw.checkbox.isChecked) {
                checkOneSort(   binding.lytHighToLaw.checkbox,    binding.lytLowTohigh.checkbox,Constants.HighToLaw)

            } else {
                checkOneSort(   binding.lytLowTohigh.checkbox,    binding.lytHighToLaw.checkbox,Constants.LowTohigh)

            }

        }
        binding.lytLowTohigh.checkbox.setOnClickListener {
            if (binding.lytLowTohigh.checkbox.isChecked) {
                checkOneSort(   binding.lytLowTohigh.checkbox,    binding.lytHighToLaw.checkbox,Constants.LowTohigh)

            } else {
                checkOneSort(   binding.lytHighToLaw.checkbox,    binding.lytLowTohigh.checkbox,Constants.HighToLaw)

            }

        }

        setUpNationalities()

    }

    fun checkOneSort(checked: CheckBox, uncheck: CheckBox,sort:Int) {
checked.isChecked= true
        uncheck.isChecked= false
        this.sort=sort


    }
    private fun setUpNationalities() {
        var chooseDist = NationalitiesItem(-1, resources.getString(R.string.choose))
        nationalities?.add(chooseDist)
        var spinneerHelper = activity?.let {
            nationalities?.let { it1 ->
                SpinnerHelper(
                    it, it1, true
                )
            }
        }
        spinneerHelper?.setAdapter(binding.spinnerDistrict)
        binding.spinnerDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    var pos = nationalities?.get(position)?.id
                    if (pos != null) {
                        nationalitiesId = pos
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        if (nationalitiesId != 0) {
            var pos = nationalitiesId?.let { spinneerHelper?.getIndexOf(it) }

            pos?.let { binding.spinnerDistrict.setSelection(it) }

        }
    }

}