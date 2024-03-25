package com.example.profession.ui.fragments.checkout


import android.os.Build
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.base.BaseFragment
 import com.example.profession.databinding.FragmentChooseTimeBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
 import com.example.profession.util.SpinnerHelperInt
import com.example.profession.util.ext.setTint
import com.example.profession.util.ext.toDate
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
 import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChooseTimeFragment : BaseFragment<FragmentChooseTimeBinding>() {
    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()

    override fun onFragmentReady() {

        setupUi()
        getTime()
        onClick()
        setUpHours()
        setUpMintues()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
findNavController().navigateUp()        }
        binding.btnOk.setOnClickListener {
if(mViewModel.hourToVisit          .isBlank()){
    showToast(resources.getString(R.string.please_choose_time_to_Visit))
}

else{


    findNavController().navigate(R.id.providerTaxFragment)
}
        }
        binding.lytPlus.setOnClickListener {
            mViewModel.hoursCount=   mViewModel.hoursCount+1
            binding.tvCount.text = mViewModel.hoursCount.toString()
        }

        binding.lytMinus.setOnClickListener {
            if(mViewModel.hoursCount != 1) {
                mViewModel.hoursCount--
                binding.tvCount.text = mViewModel.hoursCount.toString()
            }
        }
    }
    var current = ""

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime() {
        var i = 1
        val cal: Calendar = GregorianCalendar()
        //2023-01-31
    if(  mViewModel.current==null) {
        current = SimpleDateFormat("MMM dd", Locale.ENGLISH).format(cal.time)
        mViewModel.current = SimpleDateFormat("YYYY-MM-dd",Locale.ENGLISH).format(cal.time)
        binding.tvDay.text = current
    }else{
         current = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH).format(   mViewModel.current?.toDate("YYYY-MM-dd"))
        binding.tvDay.text = current
    }

        binding.lytNext.setOnClickListener {
            binding.ivPrevious.setTint(resources.getColor(R.color.black))
            cal.add(Calendar.DATE, 1)
            i++
            current = SimpleDateFormat("MMM dd", Locale.ENGLISH).format(cal.time)
            mViewModel.   current= SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH).format(cal.time)
            binding.tvDay.text = current

        }
        binding.lytPrev.setOnClickListener {
            if (i > 1) {
                i--
                cal.add(Calendar.DATE, -1)
                mViewModel.   current= SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH).format(cal.time)
                current = SimpleDateFormat("MMM dd", Locale.ENGLISH).format(cal.time)

                binding.tvDay.text = current
            }
            if (i == 1) binding.ivPrevious.setTint(resources.getColor(R.color.gray_800))
        }
        binding.tvAm.setOnClickListener {
            if (binding.tvAm.isChecked) {
                binding.tvAm.text = resources.getText(R.string.pm)
                mViewModel.am = "م"
            } else {
                binding.tvAm.text = resources.getText(R.string.am)
                mViewModel.am = "ص"
            }
        }

    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.tvTitleHeader.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.tvTitleHeader.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
     binding.tvCount.setText(   mViewModel.hoursCount.toString() )}
    var listHours:List<String>  =	  listOf( "01","02","03","04","05","06","07","08","09","10","11","12","12")
    var listMintue=	  listOf("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        ,"32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","59")

    private fun setUpHours() {
         var spinneerHelper = requireActivity()?.let {
             listHours.let { it1 ->
                 SpinnerHelperInt(
                     it, it1, true
                 )
             }
        }
        binding.spinnerHour?.let { spinneerHelper?.setAdapter(it) }
        binding.spinnerHour?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    var value = listHours.get(position)
                    if (value != null) {
                        mViewModel.hourToVisit = value.toString()
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
       /* if (   mViewModel.hourToVisit != null||mViewModel.hourToVisit != "") {
            var pos =    mViewModel.hourToVisit?.let { spinneerHelper?.getIndexOf(it) }

            pos?.let { binding.spinnerHour?.setSelection(it) }

        }*/
    }
    private fun setUpMintues() {
        var spinneerHelper = activity?.let {
            listMintue.let { it1 ->
                SpinnerHelperInt(
                    it, it1, true
                )
            }
        }
        binding.spinnerMin?.let { spinneerHelper?.setAdapter(it) }
        binding.spinnerMin?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    var value = listMintue.get(position)
                    if (value != null) {
                        mViewModel.mintueToVisit = value
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    /*    if (mViewModel.mintueToVisit != null || mViewModel.mintueToVisit != "") {

                var pos = mViewModel.mintueToVisit ?.let { spinneerHelper?.getIndexOf(it) }

                pos?.let { binding.spinnerMin?.setSelection(it) }


        }
*/
    }
}