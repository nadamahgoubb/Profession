package com.example.profession.data.dataSource.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class ProvidersResponse(
    @SerializedName("providers" ) var providers : ArrayList<Providers> = arrayListOf()

) : Parcelable

@Parcelize
data class ReviewsResponse(
    @SerializedName("reviews" ) var Reviews : ArrayList<Reviews> = arrayListOf()
) : Parcelable

@Parcelize

data class Providers (

    @SerializedName("id"                         ) var id                       : Int?                   = null,
    @SerializedName("name"                       ) var name                     : String?                = null,
    @SerializedName("type"                       ) var type                     : String?                = null,
    @SerializedName("national_id"                ) var nationalId               : String?                = null,
    @SerializedName("commercial_registration_no" ) var commercialRegistrationNo : String?                = null,
    @SerializedName("country_id"                 ) var countryId                : Int?                   = null,
    @SerializedName("city_id"                    ) var cityId                   : Int?                   = null,
    @SerializedName("nationality_id"             ) var nationalityId            : Int?                   = null,
    @SerializedName("email"                      ) var email                    : String?                = null,
    @SerializedName("phone"                      ) var phone                    : String?                = null,
    @SerializedName("country_code"               ) var countryCode              : String?                = null,
    @SerializedName("service_id"                 ) var serviceId                : Int?                   = null,
    @SerializedName("bank_id"                    ) var bankId                   : Int?                   = null,
    @SerializedName("previous_experience"        ) var previousExperience       : String?                = null,
    @SerializedName("years_experience"           ) var yearsExperience          : String?                = null,
    @SerializedName("hour_price"                 ) var hourPrice                : Double?                = null,
    @SerializedName("photo"                      ) var photo                    : String?                = null,
    @SerializedName("personal_id_photo"          ) var personalIdPhoto          : String?                = null,
    @SerializedName("balance"                    ) var balance                  : Int?                   = null,
    @SerializedName("total_rate"                 ) var totalRate                : Double?                   = null,
    @SerializedName("count_reviews"              ) var countReviews             : Int?                   = null,
    @SerializedName("account_number"             ) var accountNumber            : String?                = null,
    @SerializedName("account_name"               ) var accountName              : String?                = null,
    @SerializedName("iban_number"                ) var ibanNumber               : String?                = null,
    @SerializedName("address"                    ) var address                  : String?                = null,
    @SerializedName("lat"                        ) var lat                      : String?                = null,
    @SerializedName("lon"                        ) var lon                      : String?                = null,
    @SerializedName("password"                   ) var password                 : String?                = null,
    @SerializedName("email_verified_at"          ) var emailVerifiedAt          : String?                = null,
    @SerializedName("fcm_token"                  ) var fcmToken                 : String?                = null,
    @SerializedName("mobile_id"                  ) var mobileId                 : Int?                   = null,
    @SerializedName("remember_token"             ) var rememberToken            : String?                = null,
    @SerializedName("distance"                   ) var distance                 : String?                = null,
    @SerializedName("sub_services"               ) var subServices              :  @RawValue ArrayList<SubServiceItemsResponse> = arrayListOf(),
  var serviceCostBeforeTax              :  Double? = null,
  var serviceTotalCost              :  Double? = null,
   var serviceTax              :       Double? = null ,
    var choosen : Boolean

) : Parcelable

@Parcelize
data class Reviews (
@SerializedName("id"             ) var id            : Int?    = null,
@SerializedName("provider_id"    ) var providerId    : Int?    = null,
@SerializedName("provider_name"  ) var providerName  : String? = null,
@SerializedName("provider_photo" ) var providerPhoto : String? = null,
@SerializedName("user_id"        ) var userId        : Int?    = null,
@SerializedName("user_name"      ) var userName      : String? = null,
@SerializedName("user_photo"     ) var userPhoto     : String? = null,
@SerializedName("order_id"       ) var orderId       : Int?    = null,
@SerializedName("rate"           ) var rate          : Double? = null,
@SerializedName("comment"        ) var comment       : String? = null,
@SerializedName("created_at"     ) var createdAt     : String? = null

) : Parcelable
