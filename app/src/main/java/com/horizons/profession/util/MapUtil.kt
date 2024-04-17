package com.horizons.profession.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*


object MapUtil {

    fun addMarker(googleMap: GoogleMap, pos: LatLng, title: String? = null,bitmap: BitmapDescriptor): Marker? {
        val marker = googleMap.addMarker(MarkerOptions()
            .position(pos)
            .icon( bitmap)
            .flat(true))

        return marker
    }

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
     fun  bitmapDescriptorFromVector(context: Context, vectorResId:Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
         vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
         val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
         val canvas =  Canvas(bitmap)
         vectorDrawable.draw(canvas)
         return BitmapDescriptorFactory.fromBitmap(bitmap)
     }
}