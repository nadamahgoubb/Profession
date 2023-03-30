package com.example.profession.ui.fragments


import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.databinding.FragmentChooseTimeBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.ChooseTimeAdapter
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.ui.listener.ChooseTimeOnClickListener
import com.example.profession.util.ext.init
import com.example.profession.util.ext.setTint
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@AndroidEntryPoint
class ChooseTimeFragment : BaseFragment<FragmentChooseTimeBinding>() {
    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()

    override fun onFragmentReady() {

        setupUi()
        getTime()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnOk.setOnClickListener {
if(binding.etHour.text.toString().isBlank()){
    showToast(resources.getString(R.string.please_choose_time_to_Visit))
}

else{

    if(!binding.etMin.text.toString().isBlank()){
        mViewModel.mintueToVisit= binding.etMin.text.toString()
    }
    mViewModel.hourToVisit= binding.etHour.text.toString()
    findNavController().navigate(R.id.providerTaxFragment)
}
        }
        binding.lytPlus.setOnClickListener {
            mViewModel.hoursCount=   mViewModel.hoursCount+1
            binding.tvCount.setText(mViewModel.hoursCount.toString())
        }

        binding.lytMinus.setOnClickListener {
            if(mViewModel.hoursCount != 1) {
                mViewModel.hoursCount--
                binding.tvCount.setText(mViewModel.hoursCount.toString())
            }
        }
    }
    var current = ""

    private fun getTime() {
        var i = 1
        val cal: Calendar = GregorianCalendar()
        //2023-01-31
       current = SimpleDateFormat("MMM dd").format(cal.getTime())
        mViewModel. current = SimpleDateFormat("YYYY-MM-dd").format(cal.getTime())
        binding.tvDay.setText(current)

        binding.lytNext.setOnClickListener {
            binding.ivPrevious.setTint(resources.getColor(R.color.black))
            cal.add(Calendar.DATE, 1)
            i++
            current = SimpleDateFormat("MMM dd").format(cal.getTime())
            mViewModel.   current= SimpleDateFormat("YYYY-MM-dd").format(cal.getTime())
            binding.tvDay.setText(current)

        }
        binding.lytPrev.setOnClickListener {
            if (i > 1) {
                i--
                cal.add(Calendar.DATE, -1);
                mViewModel.   current= SimpleDateFormat("YYYY-MM-dd").format(cal.getTime())
                current = SimpleDateFormat("MMM dd").format(cal.getTime())

                binding.tvDay.setText( current)
            }
            if (i == 1) binding.ivPrevious.setTint(resources.getColor(R.color.gray_800))
        }
        binding.tvAm.setOnClickListener {
            if (binding.tvAm.isChecked) {
                binding.tvAm.setText(resources.getText(R.string.pm))
                mViewModel.am = "م"
            } else {
                binding.tvAm.setText(resources.getText(R.string.am))
                mViewModel.am = "ص"
            }
        }

    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.getTotalScrollRange()) {
                // If collapsed, then do this
                binding.tvTitleHeader.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.tvTitleHeader.setVisibility(View.VISIBLE);
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
    }


}