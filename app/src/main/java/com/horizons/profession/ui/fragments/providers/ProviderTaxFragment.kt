package com.horizons.profession.ui.fragments.providers

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.horizons.profession.R
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.response.Providers
import com.horizons.profession.databinding.FragmentProviderTaxBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.ui.adapter.ProviderSelectedAdapter
import com.horizons.profession.ui.adapter.ProviderSelectedsClickListener
import com.horizons.profession.ui.fragments.subService.CreateOrdersAction
import com.horizons.profession.ui.fragments.subService.CreateOrdersViewModel
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.init
import com.horizons.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderTaxFragment : BaseFragment<FragmentProviderTaxBinding>(),
    ProviderSelectedsClickListener {

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
        binding.swiperefreshHome.setOnRefreshListener {
            mViewModel.getTaxes()
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
        }
    }

    private fun onclick() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnOrder.setOnClickListener {
            if(mViewModel.selectedProviders.isEmpty()) showToast(resources.getString(R.string.please_choose_providers_first))
          else   findNavController().navigate(R.id.checkOutFragment)
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
                    mViewModel.tax = it
                    getCostAndTax(it)
                }
            }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    fun getCostAndTax(tax: Double) {
         adapter.notifyDataSetChanged()
         for (i in mViewModel.selectedProviders) {
            i.serviceCostBeforeTax = i.hourPrice?.toDoubleOrNull()?.let { mViewModel.hoursCount.times(it) }
            i.serviceTax = i.serviceCostBeforeTax?.let { tax.times(it).div(100) }
            i.serviceTotalCost = i.serviceTax?.let { i.serviceCostBeforeTax?.plus(it) }
        }
        adapter.list = mViewModel.selectedProviders
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
        adapter.deleteItem(position)
        mViewModel.selectedProviders.remove(item)
    }

}