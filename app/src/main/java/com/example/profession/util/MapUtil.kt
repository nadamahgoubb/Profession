package com.example.profession.util

import android.annotation.SuppressLint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


object MapUtil {

  /*  fun addMarker(googleMap: GoogleMap, pos: LatLng, title: String? = null): Marker? {
        val marker = googleMap.addMarker()

       val markerOptions: MarkerOptions=


           {
            position(pos)
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            flat(true)
            if (title != null)
                title(title)
        }
        return marker
    }*/

    fun moveCameraAt(googleMap: GoogleMap, pos: LatLng, animate: Boolean = false) {
        val factory = CameraUpdateFactory.newLatLngZoom(pos, 16f)
        with(googleMap) {
            if (!animate)
                moveCamera(factory)
            else
                animateCamera(factory)
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    fun disableMarkerClick(googleMap: GoogleMap, disable: Boolean) {
        if (disable)
            googleMap.setOnMarkerClickListener { true }
    }

}