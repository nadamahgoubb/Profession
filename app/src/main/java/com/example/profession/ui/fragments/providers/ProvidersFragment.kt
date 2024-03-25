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
import com.example.profession.data.dataSource.response.NationalitiesItem
import com.example.profession.ui.adapter.ProviderClickListener
import com.example.profession.ui.adapter.ProvidersAdapter
import com.example.profession.ui.dialog.FilterBottomSheet
import com.example.profession.ui.dialog.OnFilterClick
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.Constants
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.showActivity
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
            if (mViewModel.allProviders.size == 0) getProviders()
            else showProviders()

            observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
           mViewModel.getProviders()
            mViewModel.selectedProviders= arrayListOf()
            nationalitiesId  = null
            sort  = null
            rate  = null
            distance = null
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

                mViewModel.allProviders = action.data.providers
                showProviders()

            }  is CreateOrdersAction.ShowNationalitesResponse -> {
                showProgress(false)

            showFilterBottomSheet(action.data.nationalities)


        }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    private fun showProviders() {
        if (mViewModel.allProviders.size > 0) {
            binding.lytEmptyState.isVisible = false
            binding.lytData.isVisible = true
            adapter.list = mViewModel.allProviders
            adapter.notifyDataSetChanged()
        } else {
            binding.lytEmptyState.isVisible = true
            binding.lytData.isVisible = false
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
            mViewModel.getNationalities()
        }
        binding.btnOrder.setOnClickListener {
            if (mViewModel.selectedProviders.isNullOrEmpty()) showToast(resources.getString(R.string.please_choose_providers_first))
            else findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.btnGohome.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
    }
    var nationalitiesId : Int? = null
    var sort: Int? = null
    var rate: Double? = null
    var distance: Double? = null

    fun showFilterBottomSheet(nationalities: ArrayList<NationalitiesItem>?) =
        FilterBottomSheet.newInstance(object : OnFilterClick {
            override fun onFilterSubmitted(
                sort: Int?,
                nationality: Int?,
                rates: Double?,
                distance: Double?
            ) {
                try{
                var    allproviders :ArrayList<Providers> = mViewModel.allProviders

                nationality?.let{
                    allproviders = mViewModel.allProviders.filter {
                        it.nationalityId == nationality
                    } as ArrayList<Providers>
                }
                rates?.let {
                    var c=    allproviders .intersect((mViewModel.allProviders.filter {
                        it.totalRate!! > rates.toString()
                    } as ArrayList<Providers>))
                    if(c.size>0)allproviders = c.toList() as ArrayList<Providers>
                    else allproviders = arrayListOf()

                 }
                distance?.let {

                    var c=       allproviders .intersect((mViewModel.allProviders.filter {
                        it.distance?.toDoubleOrNull()?.compareTo(distance)!! < 1
                    } as ArrayList<Providers>))
                    if(c.size>0)allproviders = c.toList() as ArrayList<Providers>
                    else allproviders = arrayListOf()



                }
                sort?.let {

                    if (sort.equals(Constants.HighToLaw)) {
                        allproviders.sortByDescending {
                            it.hourPrice
                        }
                    } else {
                        allproviders.sortBy {
                            it.hourPrice
                        }
                    }
                }
                if(allproviders.size>0) {
                    adapter.list = allproviders
                    adapter.notifyDataSetChanged()
                     this@ProvidersFragment.distance= distance
                    this@ProvidersFragment.sort= sort
                    this@ProvidersFragment.nationalitiesId= nationality

                }else{
                    showToast(getString(R.string.n_provider))

                }
                }catch (e:Exception){
                    showToast(getString(R.string.n_provider))

                }
            }


        },nationalities, nationalitiesId ,
      sort,
      rate,
     distance ).show(childFragmentManager, FilterBottomSheet::class.java.canonicalName)

    override fun onProviderDetailsClicked(item: Providers?) {
        var bundle = Bundle()
        bundle.putParcelable(Constants.PROVIDERS, item)
        findNavController().navigate(R.id.providerProfileFragment, bundle)
    }

    override fun onProviderAddedClicked(items: ArrayList<Providers>) {
        mViewModel.selectedProviders = items
        (  mViewModel.selectedProviders ) .distinct()
     }
}