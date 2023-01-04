package com.example.profession.ui.fragments.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat

import com.example.profession.R
import com.example.profession.databinding.FragmentMapBinding
import com.example.profession.util.ExpandAnimation.collapse
import com.example.profession.util.MapUtil
import com.example.profession.util.PermissionManager
import com.example.profession.util.WWLocationManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.Marker

import java.lang.Exception


interface onLocationClick {
    fun onClick( lat :Double?, long :Double?,address :String?)
}

@AndroidEntryPoint
class MapBottomSheet(var onClick: onLocationClick) :     BottomSheetDialogFragment()
   , OnMapReadyCallback {

    private var lat: Double?= null
    private var long: Double?= null
    private lateinit var binding: FragmentMapBinding

    private var loc: Location? = null
    private var address: String? = null

     companion object {
        fun newInstance(onClick: onLocationClick): MapBottomSheet {
            val args = Bundle()
            val f = MapBottomSheet(onClick)
            f.arguments = args
            return f
        }
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var selectedLocation: LatLng
    private var marker: Marker? = null

    @Inject
    lateinit var locationManager: WWLocationManager

    @Inject
    lateinit var permissionManager: PermissionManager


    private fun getLocationNow() {
        locationManager.getLastKnownLocation { location ->
            loc = location
            loc?.latitude?.let { loc?.longitude?.let { it1 -> showlocation(it, it1) } }

        }
    }
    @SuppressLint("CutPasteId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater)
        onClick()
        return binding.root
    }

    private fun showlocation(latitude:Double, longitude:Double) {
        lat=latitude
        long= longitude
        onMapReady(googleMap, latitude, longitude)
        address= locationManager.getAddress(latitude, longitude)
        binding?.tvAddress?.text =
            address
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
    var     icon = MapUtil.bitmapDescriptorFromVector(requireContext(), R.drawable.marker)
        marker = MapUtil.addMarker(gm, position, "",icon)    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val location = CameraUpdateFactory.newLatLngZoom(selectedLocation, 12f)

        this.googleMap.uiSettings.isZoomControlsEnabled = true
        this.googleMap.animateCamera(location)
        getLocationNow()
        this.googleMap.setOnCameraIdleListener {
            selectedLocation = LatLng(
                this.googleMap.cameraPosition.target.latitude,
                this.googleMap.cameraPosition.target.longitude
            )
        }
    }

    private fun onClick() {
        binding?.locationCard?.setOnClickListener {
            collapse(binding?.locationCard!!)
        }
        binding?.btnDone?.setOnClickListener {
            onClick.onClick(lat, long, address)



            dismiss()
        }
    }


    override fun onResume() {
        super.onResume()
        binding?.map?.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding?.map?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding?.map?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.map?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.map?.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.map?.onCreate(savedInstanceState)
        binding?.map?.onResume();
        //    binding.map.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.map?.getMapAsync(OnMapReadyCallback { mMap ->
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
            mMap.setMyLocationEnabled(true)
            googleMap?.isMyLocationEnabled = true
            getLocationNow()
            googleMap.setOnMapClickListener {
                showlocation(it.latitude, it.longitude)
            }

        })

        onClick()

    }

}