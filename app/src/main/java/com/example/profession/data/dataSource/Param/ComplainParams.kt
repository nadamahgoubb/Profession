package com.example.profession.data.dataSource.Param

import android.os.Parcelable
import com.example.profession.base.PagingParams
import kotlinx.android.parcel.Parcelize
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Field
import java.io.File


@Parcelize
data class ComplainParams (

    var user_id: String? = null,
    var title: String? = null,
    var content: String? = null,

    ) : Parcelable


