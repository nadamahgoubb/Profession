package com.example.profession.ui.fragments.subService


import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentSubServiceBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.util.*
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubServiceFragment : BaseFragment<FragmentSubServiceBinding>() {

    @Inject
    lateinit var permissionManager: PermissionManager
    private lateinit var parent: MainActivity

    @Inject
    lateinit var locationManager: WWLocationManager
    override fun onFragmentReady() {
        setupBottomCard()
        setupUi()
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
        private fun setupUi() {
                parent = requireActivity() as MainActivity
                parent.showBottomNav(false)
                parent.showSideNav(false)

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
}