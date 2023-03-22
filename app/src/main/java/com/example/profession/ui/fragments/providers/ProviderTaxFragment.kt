package com.example.profession.ui.fragments.providers


import androidx.fragment.app.activityViewModels
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.databinding.FragmentProviderTaxBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.ProviderClickListener
import com.example.profession.ui.adapter.ProviderSelectedAdapter
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.observe

class ProviderTaxFragment : BaseFragment<FragmentProviderTaxBinding>(), ProviderClickListener {

    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter: ProviderSelectedAdapter
    override fun onFragmentReady() {
        onclick()
        setupUi()
        mViewModel.apply {
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

            }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        adapter = ProviderSelectedAdapter(this)
        binding.rvProviders.init(requireContext(), adapter, 2)
    }

    override fun onProviderDetailsClicked(item: Providers?) {
        TODO("Not yet implemented")
    }

    override fun onProviderAddedClicked(items: ArrayList<Providers>) {
        TODO("Not yet implemented")
    }


}