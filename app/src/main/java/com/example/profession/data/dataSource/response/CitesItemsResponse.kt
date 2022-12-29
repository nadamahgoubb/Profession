package com.example.profession.data.dataSource.response

import com.google.gson.annotations.SerializedName


data class CitesItemsResponse(

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("country_id"        ) var countryId       : Int?    = null,


)

