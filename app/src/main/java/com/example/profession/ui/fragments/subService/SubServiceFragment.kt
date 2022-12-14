package com.example.profession.ui.fragments.subService


import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.profession.R
import com.example.profession.databinding.FragmentSubServiceBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.ServicesItemsResponse
import com.example.profession.data.dataSource.response.SubServiceItemsResponse
import com.example.profession.ui.adapter.ServicesHomeAdapter
import com.example.profession.ui.adapter.SubServiceAdapter
import com.example.profession.ui.fragments.home.HomeAction
import com.example.profession.ui.fragments.home.HomeViewModel
import com.example.profession.ui.listener.SubServiceListener
import com.example.profession.util.*
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.loadImage
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubServiceFragment : BaseFragment<FragmentSubServiceBinding>(), SubServiceListener {
    private val mViewModel: HomeViewModel by viewModels()
    lateinit var adapterSubServices: SubServiceAdapter

    @Inject
    lateinit var permissionManager: PermissionManager
    private lateinit var parent: MainActivity

    @Inject
    lateinit var locationManager: WWLocationManager
    override fun onFragmentReady() {
        var item =arguments?.getParcelable<ServicesItemsResponse>(Constants.SERVICE)
        setupUi(item)
        onClick()
        initAdapter()
        mViewModel.apply {
            getSubService(item?.id.toString())

            observe(viewState) {
                handleViewState(it)
            }
        }
    }

    private fun initAdapter() {

        adapterSubServices  = SubServiceAdapter(this, requireContext())

        binding.recServices.init(requireContext(), adapterSubServices, 2)
        binding.recServices.addItemDecoration( SimpleDividerItemDecoration(requireContext()));


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapterSubServices.loadStateFlow.collect {
                     binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        adapterSubServices.addLoadStateListener { loadState ->

            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
              //  binding.lytEmptyState.visibility = View.GONE
                binding.lytData.visibility = View.VISIBLE
            }
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapterSubServices.itemCount < 1) {
                binding.lytData.visibility = View.GONE

            //    binding.lytEmptyState.visibility = View.VISIBLE
            } else {
              //  binding.lytEmptyState.visibility = View.GONE
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


    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }    }

    private fun handleViewState(action: HomeAction) {
        when (action) {
            is HomeAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is    HomeAction.ShowSubService -> {
                showProgress(false)
                lifecycleScope.launch {
                    adapterSubServices.submitData(action.data)
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


        private fun setupUi(item: ServicesItemsResponse?) {
            setupBottomCard()
            parent = requireActivity() as MainActivity
                parent.showBottomNav(false)
                parent.showSideNav(false)
binding.ivProfile.loadImage(Constants.BaseUrl_Images+item?.icon)

      binding.tvTitle.setText(item?.name.toString())

            binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (Math.abs(verticalOffset) ==   binding.appBarLayout.getTotalScrollRange()) {
                    // If collapsed, then do this
                    binding.ivProfile.setVisibility(View.GONE);
               binding.lytImg.setVisibility(View.GONE);
                 } else if (verticalOffset == 0) {
                 binding.lytImg.setVisibility(View.VISIBLE);
                    binding.ivProfile.setVisibility(View.VISIBLE);
                 } else {
                    // Somewhere in between
                    // Do according to your requirement
                }

                   })

        }


    private fun setupBottomCard() {
        binding.ivUp.setOnClickListener {
            up()
        }
        binding.ivDawn.setOnClickListener {
            if (!binding.lytCustomerService.isVisible) up()
            else dawn()

        }
        binding.tvUp.setOnClickListener {
            up()

        }
        binding.btnDone.setOnClickListener {
            checkLocation()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun checkLocation() {
        if (permissionManager.hasAllLocationPermissions()) {
            checkIfLocationEnabled()
        } else {
            permissionsLauncher?.launch(permissionManager.getAllLocationPermissions())
        }
    }

    private fun checkIfLocationEnabled() {
        if (locationManager.isLocationEnabled()) {
            openMaps()
        } else {
            activity?.let { locationManager.buildAlertMessageNoGps(it, locationSettingLauncher) }
        }
    }

    private fun openMaps() {
        findNavController().navigate(R.id.mapFragment)
    }


    private val permissionsLauncher = requestAppPermissions { allIsGranted, _ ->
        if (allIsGranted) {
            checkIfLocationEnabled()
        } else {
            Toast.makeText(
                activity, getString(R.string.not_all_permissions_accepted), Toast.LENGTH_LONG
            ).show()
        }
    }

    private val locationSettingLauncher = openLocationSettingsResultLauncher {
        checkIfLocationEnabled()
    }

    fun up() {
        if (!binding.lytCustomerService.isVisible) {

            ExpandAnimation.expand(binding.lytCustomerService)
            binding.ivUp.visibility = View.GONE
            binding.tvUp.visibility = View.GONE
            binding.ivDawn.rotation = 90F
        }
    }

    fun dawn() {
        if (binding.lytCustomerService.isVisible) {

            ExpandAnimation.collapse(binding.lytCustomerService)
            binding.ivUp.visibility = View.VISIBLE
            binding.tvUp.visibility = View.VISIBLE
            binding.ivDawn.rotation = 270F

        }
    }

    override fun onSubServiceClickListener(item: SubServiceItemsResponse) {
     }
}