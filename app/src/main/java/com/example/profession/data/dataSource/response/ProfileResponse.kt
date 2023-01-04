package com.example.profession.data.dataSource.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("id"                ) var id              : Int?     = null,
    @SerializedName("name"              ) var name            : String?  = null,
    @SerializedName("phone"             ) var phone           : String?  = null,
    @SerializedName("country_code"      ) var countryCode     : String?  = null,
    @SerializedName("email"             ) var email           : String?  = null,
    @SerializedName("country_id"        ) var countryId       : Int?     = null,
    @SerializedName("city_id"           ) var cityId          : Int?     = null,
    @SerializedName("lat"               ) var lat             : String?  = null,
    @SerializedName("lon"               ) var lon             : String?  = null,
    @SerializedName("photo"             ) var photo           : String?  = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String?  = null,
    @SerializedName("fcm_token"         ) var fcmToken        : String?  = null,
    @SerializedName("mobile_id"         ) var mobileId        : Int?     = null,
    @SerializedName("created_at"        ) var createdAt       : String?  = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?  = null,
    @SerializedName("country_name"      ) var countryName     : String?  = null,
    @SerializedName("city_name"         ) var cityName        : String?  = null,
    @SerializedName("country"           ) var country         : Country? = Country(),
    @SerializedName("city"              ) var city            : City?    = City()

)

data class Country (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("lat"        ) var lat       : String? = null,
    @SerializedName("lon"        ) var lon       : String? = null,
    @SerializedName("deleted_at" ) var deletedAt : String? = null,
    @SerializedName("name"       ) var name      : String? = null

)

data class City (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("country_id" ) var countryId : Int?    = null,
    @SerializedName("lat"        ) var lat       : String? = null,
    @SerializedName("lon"        ) var lon       : String? = null,
    @SerializedName("deleted_at" ) var deletedAt : String? = null,
    @SerializedName("name"       ) var name      : String? = null

)