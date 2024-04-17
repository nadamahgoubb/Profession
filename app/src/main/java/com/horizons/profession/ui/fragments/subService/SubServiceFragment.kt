package com.horizons.profession.ui.fragments.subService


 import android.util.Log
 import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentSubServiceBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
 import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
 import com.horizons.profession.data.dataSource.response.ServicesItemsResponse
import com.horizons.profession.data.dataSource.response.SubServiceItemsResponse
import com.horizons.profession.ui.adapter.SubServiceAdapter
import com.horizons.profession.ui.fragments.home.HomeAction
import com.horizons.profession.ui.fragments.home.HomeViewModel
import com.horizons.profession.ui.listener.SubServiceListener
import com.horizons.profession.util.*
 import com.horizons.profession.util.ext.*
 import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SubServiceFragment : BaseFragment<FragmentSubServiceBinding>(), SubServiceListener {
    private val mViewModel: HomeViewModel by viewModels()
    private val mViewModelCreateOrder: CreateOrdersViewModel by activityViewModels()
    lateinit var adapterSubServices: SubServiceAdapter

    @Inject
    lateinit var permissionManager: PermissionManager
    private lateinit var parent: MainActivity

    @Inject
    lateinit var locationManager: WWLocationManager
    override fun onFragmentReady() {
        var item = arguments?.getParcelable<ServicesItemsResponse>(Constants.SERVICE)
        setupUi(item)
        mViewModelCreateOrder.serviceId = item?.id
        onClick()
        initAdapter()
        mViewModel.apply {
            mViewModelCreateOrder.  selectedSubservice .clear()
            getSubService(item?.id.toString())
            observe(viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
           mViewModel. getSubService(item?.id.toString())
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
        }
        mViewModelCreateOrder.  selectedSubservice .clear()

    }

    private fun initAdapter() {

        adapterSubServices = SubServiceAdapter(this, requireContext())

        binding.recServices.init(requireContext(), adapterSubServices, 2)
        binding.recServices.addItemDecoration(SimpleDividerItemDecoration(requireContext()))


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
                binding.lytEmptyState.visibility = View.GONE
                binding.lytData.visibility = View.VISIBLE
            }
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapterSubServices.itemCount < 1) {
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


    }



    private fun handleViewState(action: HomeAction) {
        when (action) {
            is HomeAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is HomeAction.ShowSubService -> {
                showProgress(false)
                lifecycleScope.launch {
                    mViewModelCreateOrder.  selectedSubservice .clear()
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
        onClick()
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        binding.ivProfile.loadImage(item?.icon , isCircular = true)

        binding.tvTitle.text = item?.name.toString()

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.ivProfile.visibility = View.GONE
                binding.lytImg.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.lytImg.visibility = View.VISIBLE
                binding.ivProfile.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })

    }


    private fun onClick() {

        binding.btnDone.setOnClickListener {
            if (PrefsHelper.getUserData().isNull()) {

                findNavController().navigate(R.id.loginFirstBotomSheetFragment)

        }else{
                if (mViewModelCreateOrder.selectedSubservice.size<1) showToast(resources.getString(R.string.please_select_subServie))
                else   checkLocation()
            }

        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnGohome.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
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


    override fun onSubServiceClickListener(items: ArrayList<SubServiceItemsResponse>) {
         mViewModelCreateOrder.  selectedSubservice = items
        Log.d("mViewModelCreateOrder", mViewModelCreateOrder.selectedSubservice.size.toString())
    }
}