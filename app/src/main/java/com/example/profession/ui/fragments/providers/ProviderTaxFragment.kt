package com.example.profession.ui.fragments.providers


import androidx.fragment.app.activityViewModels
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.databinding.FragmentProviderTaxBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.ProviderClickListener
import com.example.profession.ui.adapter.ProviderSelectedAdapter
import com.example.profession.ui.adapter.ProviderSelectedsClickListener
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.observe

class ProviderTaxFragment : BaseFragment<FragmentProviderTaxBinding>(), ProviderSelectedsClickListener {

    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter: ProviderSelectedAdapter
    override fun onFragmentReady() {
        onclick()
        setupUi()
        mViewModel.apply {
            mViewModel.getTaxes()
             observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
    }
    private fun onclick() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun handleViewState(action: CreateOrdersAction) {
        when (action) {
            is CreateOrdersAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is CreateOrdersAction.ShowTaxResponse -> {
                showProgress(false)
                action.data?.tax?.let {
                    mViewModel.tax =it
                      getCostAndTax(it) }
            }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }
 fun getCostAndTax(tax:Double) {
     for(i in mViewModel.selectedProviders){
i.serviceCostBeforeTax = i.hourPrice?.let { mViewModel.hoursCount .times(it) }
i.serviceTax= i.serviceCostBeforeTax?.let { tax.times(it) }
i.serviceTotalCost= i.serviceTax?.let { i.serviceCostBeforeTax ?.plus(it) }
     }
     adapter.list= mViewModel.selectedProviders
     adapter.notifyDataSetChanged()
 }
    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        adapter = ProviderSelectedAdapter(this)
        binding.rvProviders.init(requireContext(), adapter, 2)
    }



    override fun onProviderCancelClicked(item: Providers?, position: Int) {
mViewModel.selectedProviders.remove(item)
    adapter.deleteItem(position)
    }


}