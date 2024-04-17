package com.horizons.profession.ui.fragments.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
 import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentMapBinding
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.Param.AddressParams
import com.horizons.profession.ui.fragments.subService.CreateOrdersViewModel
import com.horizons.profession.util.ExpandAnimation.collapse
import com.horizons.profession.util.ExpandAnimation.expand
import com.horizons.profession.util.MapUtil
import com.horizons.profession.util.PermissionManager
import com.horizons.profession.util.WWLocationManager
import com.horizons.profession.util.ext.isNull
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker


import java.lang.Exception



@AndroidEntryPoint
class MapFragment :BaseFragment<FragmentMapBinding>()
   , OnMapReadyCallback {

    private lateinit var icon: BitmapDescriptor
    private var lat: Double?= null
    private var long: Double?= null

    private var loc: Location? = null
    private var address: AddressParams? = null


    private lateinit var googleMap: GoogleMap
    private lateinit var selectedLocation: LatLng
    private var marker: Marker? = null

    @Inject
    lateinit var locationManager: WWLocationManager

    @Inject
    lateinit var permissionManager: PermissionManager
    private val mViewModelCreateOrder: CreateOrdersViewModel by activityViewModels()


    private fun getLocationNow() {
        locationManager.getLastKnownLocation { location ->
            loc = location
            loc?.latitude?.let { loc?.longitude?.let { it1 -> showlocation(it, it1) } }

        }
    }

    private fun showlocation(latitude:Double, longitude:Double) {
        lat=latitude
        long= longitude
        onMapReady(googleMap, latitude, longitude)
        address= locationManager.getAddress(latitude, longitude)

      address?.let {
          mViewModelCreateOrder.address =it
          mViewModelCreateOrder.lat = latitude
           mViewModelCreateOrder.long= longitude
          binding.tvAddress?.setText( address?.address.toString())
          binding.tvCountry?.setText(
              address?.country.toString()+","+ address?.city.toString())
        }

    }

    private fun onMapReady(gm: GoogleMap, latitude:Double, longitude:Double) {
        MapUtil.disableMarkerClick(gm, true)
        val pos = LatLng(latitude, longitude)
        setMarker(gm, pos)
        MapUtil.moveCameraAt(gm, pos)

    }


    private fun setMarker(gm: GoogleMap, position: LatLng) {
        if (marker != null) {
            marker?.remove()
        }
         icon = MapUtil.bitmapDescriptorFromVector(requireContext(), R.drawable.marker)
        marker = MapUtil.addMarker(gm, position, "",icon)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val location = CameraUpdateFactory.newLatLngZoom(selectedLocation, 12f)

        this.googleMap.uiSettings.isZoomControlsEnabled = true
        this.googleMap.animateCamera(location)
        getLocationNow()
        this.googleMap.isMyLocationEnabled
       // this.googleMap.
        this.googleMap.setOnCameraIdleListener {
            selectedLocation = LatLng(
                this.googleMap.cameraPosition.target.latitude,
                this.googleMap.cameraPosition.target.longitude
            )
        }
    }

    private fun onClick() {
        binding.ivDawn?.setOnClickListener {
            collapse(binding.locationCard)
            expand(binding.cardUp)
        }
        binding.btnDone?.setOnClickListener {
if(mViewModelCreateOrder.address.isNull()) showToast(resources.getString(R.string.please_choose_your_locaion))
 else findNavController().navigate(R.id.providersFragment)        }
        binding.ivUp.setOnClickListener {
            collapse(binding.cardUp)
            expand(binding.locationCard)
        }
    }


    override fun onResume() {
        super.onResume()
        binding.map?.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.map?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.map?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map?.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map?.onCreate(savedInstanceState)
        binding.map?.onResume()
        //    binding.map.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.map?.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            if (activity?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED && activity?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED) {
            }
            mMap.isMyLocationEnabled = true
            googleMap.isMyLocationEnabled = true
            getLocationNow()
            googleMap.setOnMapClickListener {
                showlocation(it.latitude, it.longitude)
            }

        })

        onClick()

    }

    override fun onFragmentReady() {
        onClick()
        mViewModelCreateOrder.allProviders= arrayListOf()
        mViewModelCreateOrder.selectedProviders= arrayListOf()
    }

}