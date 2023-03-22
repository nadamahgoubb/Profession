package com.example.profession.data.dataSource.response

import com.google.gson.annotations.SerializedName


data class NationalitiesResponse(
    @SerializedName("nationalities") var nationalities: ArrayList<nationalitiesItem>? = arrayListOf(),
)


data class nationalitiesItem(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
)
