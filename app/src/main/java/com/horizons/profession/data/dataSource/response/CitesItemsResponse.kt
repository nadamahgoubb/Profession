package com.horizons.profession.data.dataSource.response

import com.google.gson.annotations.SerializedName


data class CitesItemsResponse(

    @SerializedName("id"                ) var id              : String?    = null,
    @SerializedName("name"              ) var name            : String? = null,
  //  @SerializedName("country_id"        ) var countryId       : Int?    = null,

    var choosen: Boolean= false

)


data class CitesResponse(

    @SerializedName("cities"                ) var cities              : ArrayList<CitesItemsResponse>?    = arrayListOf(),


)


data class CountriesResponse(

    @SerializedName("countries"                ) var countries              : ArrayList<CitesItemsResponse>?    = arrayListOf(),


)



data class NotificationResponse(

    @SerializedName("notifications"                ) var notifications              : ArrayList<NotificationsItem>?    = arrayListOf(),


)

data class NotificationsItem(

    @SerializedName("id"                ) var id              : String?    = null,
    @SerializedName("body"              ) var body            : String? = null,
    @SerializedName("created_at"        ) var created_at       : String?    = null,

    var choosen: Boolean= false

)

