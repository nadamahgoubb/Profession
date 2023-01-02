package com.example.profession.data.dataSource.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class ServicesItemsResponse(

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("name"              ) var name            : String? = null,
   @SerializedName("icon"        ) var icon       : String?    = null,

    var choosen: Boolean= false

) : Parcelable


data class SliderItemsResponse(

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("imge"              ) var image            : String? = null,

    var choosen: Boolean= false

)
data class SubServiceItemsResponse(

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("service_id"                ) var service_id              : Int?    = null,
    @SerializedName("name"              ) var name            : String? = null,

    var choosen: Boolean= false

)