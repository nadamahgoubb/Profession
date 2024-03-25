package com.example.profession.util.fcm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FcmParam(
    @SerializedName("token")
    val token: String,

) : Parcelable

