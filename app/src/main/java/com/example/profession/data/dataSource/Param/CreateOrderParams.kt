package com.example.profession.data.dataSource.Param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateOrderParams(

    var user_id: String? = null,
    var payment_method: Int?                 = null,
    var lat: String?              = null,
    var lon: String?              = null,
    var address: String?              = null,
    var order_date: String?              = null,
    var order_time: String?              = null,
    var count_hours: Int?                 = null,
    var notes: String?              = null,
    var user_phone: String?              = null,
    var country_code: String?              = null,
    var sub_service_id: ArrayList<String> = arrayListOf(),
    var providers: ArrayList<ProvidersCreateOrderParams> = arrayListOf()
) : Parcelable



@Parcelize
data class ProvidersCreateOrderParams (

  var provider_id : String?    = null,
  var tax        : Double? = null,
  var total      : String?    = null,
  var final_total : String?    = null
) : Parcelable

 data class PaymentModel(

     var id: Int?    = null,
     var title: String?    = null,
     var logo: Int? = null,
     var selected: Boolean?    = false,

     )

 data class OrderDetailsParam (

    var id : String    = ""
)

 data class PayOrderParam (

    var id : String    = "",
    var  trans_ref: String   = "",
    var  pay_token: String   = ""
)

 data class UpdateBalanceParam (

    var amount : String    = "",
    var  trans_ref: String   = "",
    var  pay_token: String   = ""
)
