package com.example.profession.data.dataSource.response

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

