package com.example.profession.data.dataSource.response

import com.google.gson.annotations.SerializedName


data class NationalitiesResponse(
    @SerializedName("nationalities") var nationalities: ArrayList<NationalitiesItem>? = arrayListOf(),
)


data class NationalitiesItem(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
)
