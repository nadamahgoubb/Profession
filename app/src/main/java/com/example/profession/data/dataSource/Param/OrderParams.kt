package com.example.profession.data.dataSource.Param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderParams(

    var userId        : Int?                 = null,
    var paymentMethod : Int?                 = null,
    var lat           : String?              = null,
    var lon           : String?              = null,
    var address       : String?              = null,
    var orderDate     : String?              = null,
    var orderTime     : String?              = null,
    var countHours    : Int?                 = null,
    var notes         : String?              = null,
    var userPhone     : String?              = null,
    var countryCode   : String?              = null,
    var subServiceId  : String?              = null,
    var providers     : ArrayList<ProvidersCreateOrderParams> = arrayListOf()
) : Parcelable



@Parcelize
data class ProvidersCreateOrderParams (

  var providerId : Int?    = null,
  var tax        : Double? = null,
  var total      : Int?    = null,
  var finalTotal : Int?    = null
) : Parcelable
