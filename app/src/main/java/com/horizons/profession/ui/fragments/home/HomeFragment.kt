package com.horizons.profession.ui.fragments.home


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentHomeBinding
import com.horizons.profession.ui.activity.MainActivity
 import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.response.ServicesItemsResponse
import com.horizons.profession.data.dataSource.response.SliderItemsResponse
import com.horizons.profession.ui.adapter.ServicesHomeAdapter
import com.horizons.profession.ui.adapter.SliderHomeAdapter
import com.horizons.profession.ui.listener.ServiceOnClickListener
import com.horizons.profession.ui.listener.SliderListener
import com.horizons.profession.util.Constants
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.init
import com.horizons.profession.util.observe
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ServiceOnClickListener, SliderListener {
    private val mViewModel: HomeViewModel by viewModels()
    lateinit var adapterServices: ServicesHomeAdapter
    lateinit var adapter_slider: SliderHomeAdapter
   private lateinit var parent: MainActivity

    override fun onFragmentReady() {
        initadapterServicess()
        setupUi()
        mViewModel.apply {
            getAllServices()
            getAllSlider()
            observe(viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
            mViewModel.  getAllServices()
            mViewModel. getAllSlider()
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
        }
    }

    private fun handleViewState(action: HomeAction) {
        when (action) {
            is HomeAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is    HomeAction.ShowService -> {
                showProgress(false)
                lifecycleScope.launch {
                    adapterServices.submitData(action.data)
                }
            }
            is    HomeAction.ShowSlider -> {
                showProgress(false)
                action.data.sliders?.let {
                    adapter_slider.list =it
                        adapter_slider.notifyDataSetChanged()
                }
            }
            is HomeAction.ShowFailureMsg ->
                action.message?.let {
                    showToast(action.message)
                    showProgress(false)

                }

            else -> {

            }
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.tvName.text = PrefsHelper.getUserData()?.name
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) ==   binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.lytHeaderDetails.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.lytHeaderDetails.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
    }


    private fun initadapterServicess() {

      adapterServices  = ServicesHomeAdapter(this, requireContext())

      binding.recServices.init(requireContext(), adapterServices, 3)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapterServices.loadStateFlow.collect {
                    binding.preProg.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        adapterServices.addLoadStateListener { loadState ->

            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                    binding.lytEmptyState.visibility = View.GONE
                     binding.lytData.visibility = View.VISIBLE
            }
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapterServices.itemCount < 1) {
                     binding.lytData.visibility = View.GONE

                  binding.lytEmptyState.visibility = View.VISIBLE
            } else {
                 binding.lytEmptyState.visibility = View.GONE
                 binding.lytData.visibility = View.VISIBLE
                // If we have an error, show a toast*/
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    /* if (it.error.message.equals(Constants.UNAUTHURAIZED_ACCESS)) {
                         showEmptyState(true)
                     } else*/
                    Toast.makeText(activity, it.error.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

            }
        }



        adapter_slider  = SliderHomeAdapter(this, requireContext())

        binding.rvOffersHome.init(requireContext(), adapter_slider, 1)


    }

    override fun onServiceClickListener(item: ServicesItemsResponse?) {
var bundle = Bundle()
        bundle.putParcelable(Constants.SERVICE, item)
        findNavController().navigate(R.id.subServiceFragment,bundle)
    }

    override fun onSliderClickListener(item: SliderItemsResponse) {
     }

}