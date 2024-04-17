package com.horizons.profession.data.dataSource.Param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class ComplainParams (

    var user_id: String? = null,
    var title: String? = null,
    var content: String? = null
    )

@Parcelize
data class ContactUsParams (

    var app_type: Int? = null,
    var   countryCode:String, var phone:String , var content: String? = null,

    ) : Parcelable


