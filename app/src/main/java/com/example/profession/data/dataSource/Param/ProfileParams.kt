package com.example.profession.data.dataSource.Param

import android.os.Parcelable
import com.example.profession.base.PagingParams
import kotlinx.android.parcel.Parcelize
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Field
import java.io.File

  class ProfileParams

@Parcelize
data class EditProfileParams (

    val name: String,
  val phone: String,
  val countryCode: String,
  val  email: String,
  val   countryId: String? = null,
  val   cityId: String? = null,
  val lat: String,
  val lon: String,
  val mobile_id: String,
  val photo: File?,
) : Parcelable


fun EditProfileParams.toMap(): Map<String, RequestBody>{

    val itemMap = hashMapOf<String, RequestBody>()

    itemMap["name"] = name.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

    itemMap["phone"] = phone.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["country_code"] = countryCode.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["email"] = email.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["country_id"] = countryId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["city_id"] = cityId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["lat"] = lat.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["lon"] = lon.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    itemMap["mobile_id"] = mobile_id.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())


    return itemMap
}


@Parcelize
data class ChangePasswordParam (

    val oldPassword: String,
    val newPassword: String,

) : Parcelable

