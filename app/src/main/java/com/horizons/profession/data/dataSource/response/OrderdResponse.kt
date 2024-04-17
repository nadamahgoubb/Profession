package com.horizons.profession.data.dataSource.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class OrderdResponse(
    @SerializedName("orders" ) var orders : ArrayList<OrdersItem> = arrayListOf()

) : Parcelable

@Parcelize
data class OrdersItem(


    @SerializedName("id"                     ) var id                    : String?                   = null,
    @SerializedName("order_id"                     ) var orderId                    : String?                   = null,
    @SerializedName("order_status"                 ) var orderStatus                : String?                = null,
    @SerializedName("user_id"                      ) var userId                     : String?                   = null,
    @SerializedName("provider_id"                  ) var providerId                 : String?                   = null,
    @SerializedName("provider_name"                ) var providerName               : String?                = null,
    @SerializedName("provider_photo"               ) var providerPhoto              : String?                = null,
    @SerializedName("provider_total_rate"          ) var providerTotalRate          : Double?                = null,
    @SerializedName("provider_phone"               ) var providerPhone              : String?                = null,
    @SerializedName("user_evaluation"               ) var user_evaluation              : Int?                = null,
    @SerializedName("user_complaint"               ) var user_complaint              : Int?                = null,
    @SerializedName("provider_previous_experience" ) var providerPreviousExperience : String?                = null,
    @SerializedName("provider_hour_price"          ) var providerHourPrice          : Double?                = null,
    @SerializedName("total"                        ) var total                      : String?                   = null,
    @SerializedName("tax"                          ) var tax                        : Double?                   = null,
    @SerializedName("final_total"                  ) var finalTotal                 : String?                   = null,
    @SerializedName("payment_method"               ) var paymentMethod              : Int?                   = null,
    @SerializedName("confirm_payment_visa"               ) var confirm_payment_visa              : Int?                   = null,
    @SerializedName("order_date"                   ) var orderDate                  : String?                = null,
    @SerializedName("order_time"                   ) var orderTime                  : String?                = null,
    @SerializedName("count_hours"                  ) var countHours                 : String?                   = null,
    @SerializedName("lat"                          ) var lat                        : String?                = null,
    @SerializedName("lon"                          ) var lon                        : String?                = null,
    @SerializedName("address"                      ) var address                    : String?                = null,
    @SerializedName("notes"                        ) var notes                      : String?                = null,
    @SerializedName("user_phone"                   ) var userPhone                  : String?                = null,
    @SerializedName("country_code"                 ) var countryCode                : String?                = null,
    @SerializedName("created_at"                   ) var createdAt                  : String?                = null,
    @SerializedName("sub_services"                 ) var subServices                : @RawValue ArrayList<SubServiceItemsResponse> = arrayListOf()

) : Parcelable
@Parcelize
data class CreateOrderdResponse(


    @SerializedName("order_id"                     ) var orderId                    : String?                   = null,
    @SerializedName("order_status"                 ) var orderStatus                : String?                = null,
    @SerializedName("user_id"                      ) var userId                     : String?                   = null,
    @SerializedName("provider_id"                  ) var providerId                 : String?                   = null,
    @SerializedName("provider_name"                ) var providerName               : String?                = null,
    @SerializedName("provider_photo"               ) var providerPhoto              : String?                = null,
    @SerializedName("provider_total_rate"          ) var providerTotalRate          : Double?                = null,
    @SerializedName("provider_phone"               ) var providerPhone              : String?                = null,
    @SerializedName("provider_previous_experience" ) var providerPreviousExperience : String?                = null,
    @SerializedName("provider_hour_price"          ) var providerHourPrice          : Double?                = null,
    @SerializedName("total"                        ) var total                      : String?                   = null,
    @SerializedName("tax"                          ) var tax                        : String?                   = null,
    @SerializedName("final_total"                  ) var finalTotal                 : String?                   = null,
    @SerializedName("payment_method"               ) var paymentMethod              : Int?                   = null,
    @SerializedName("order_date"                   ) var orderDate                  : String?                = null,
    @SerializedName("order_time"                   ) var orderTime                  : String?                = null,
    @SerializedName("count_hours"                  ) var countHours                 : String?                   = null,
    @SerializedName("lat"                          ) var lat                        : String?                = null,
    @SerializedName("lon"                          ) var lon                        : String?                = null,
    @SerializedName("address"                      ) var address                    : String?                = null,
    @SerializedName("notes"                        ) var notes                      : String?                = null,
    @SerializedName("user_phone"                   ) var userPhone                  : String?                = null,
    @SerializedName("country_code"                 ) var countryCode                : String?                = null,
    @SerializedName("created_at"                   ) var createdAt                  : String?                = null,
    @SerializedName("sub_services"                 ) var subServices                : @RawValue ArrayList<SubServiceItemsResponse> = arrayListOf()

)
  : Parcelable

@Parcelize


data class Pivot (

    @SerializedName("order_id"       ) var orderId      : String? = null,
    @SerializedName("sub_service_id" ) var subServiceId : String? = null

): Parcelable

@Parcelize
data class TaxResponse (

    @SerializedName("tax"         ) var tax        : Double?    = null

): Parcelable
