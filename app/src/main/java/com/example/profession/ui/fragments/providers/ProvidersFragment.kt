package com.example.profession.ui.fragments.providers

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProvidersBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.ui.adapter.ProviderClickListener
import com.example.profession.ui.adapter.ProvidersAdapter
import com.example.profession.ui.dialog.FilterBottomSheet
import com.example.profession.ui.dialog.OnFilterClick
import com.example.profession.ui.fragments.order.OrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.Constants
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvidersFragment : BaseFragment<FragmentProvidersBinding>(), ProviderClickListener {

    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter: ProvidersAdapter
    override fun onFragmentReady() {
        onclick()
        setupUi()
        mViewModel.apply {
            getProviders()
            observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
           mViewModel. getProviders()
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
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
            is CreateOrdersAction.ShowProviders -> {
                showProgress(false)
                showProviders(action.data.providers)

            }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    private fun showProviders(data: ArrayList<Providers>) {
        if (data.size > 0) {
            binding.lytEmptyState.isVisible= false
            binding.lytData.isVisible=true
            adapter.list = data
            adapter.notifyDataSetChanged()
        }else{
            binding.lytEmptyState.isVisible= true
            binding.lytData.isVisible=false
        }

    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        adapter = ProvidersAdapter(this)
        binding.rvProviders.init(requireContext(), adapter, 2)
    }


    private fun onclick() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.ivFilter.setOnClickListener {
            showFilterBottomSheet()
        }
        binding.btnOrder.setOnClickListener {
            if(mViewModel.selectedProviders.isNullOrEmpty())showToast(resources.getString(R.string.please_choose_providers_first))
            else findNavController().navigate(R.id.chooseTimeFragment)
        }
    }

    fun showFilterBottomSheet() {
        FilterBottomSheet.newInstance(object : OnFilterClick {
            override fun onFilterSubmitted(brandId: ArrayList<Int>?, sortId: String?) {

            }


        }).show(childFragmentManager, FilterBottomSheet::class.java.canonicalName)

    }

    override fun onProviderDetailsClicked(item: Providers?) {
        var bundle = Bundle()
        bundle.putParcelable(Constants.PROVIDERS, item)
        findNavController().navigate(R.id.providerProfileFragment, bundle )
    }

    override fun onProviderAddedClicked(items: ArrayList<Providers>) {
mViewModel.selectedProviders= items   }
}