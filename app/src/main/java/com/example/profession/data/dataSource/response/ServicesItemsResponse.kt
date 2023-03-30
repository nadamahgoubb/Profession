package com.example.profession.data.dataSource.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class ServicesItemsResponse(

@SerializedName("id"         ) var id        : String?    = null,
    @SerializedName("service_id" ) var serviceId : String?    = null,
    @SerializedName("icon"       ) var icon      : String? = null,
    @SerializedName("active"     ) var active    : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,

    var choosen: Boolean= false

) : Parcelable


data class SliderResponse(

    @SerializedName("slider"    ) var sliders              : ArrayList<SliderItemsResponse>?    = arrayListOf()

)
data class SliderItemsResponse(

    @SerializedName("id"                ) var id              : String?    = null,
    @SerializedName("imge"              ) var image            : String? = null,

    var choosen: Boolean= false

)
data class SubServiceItemsResponse(

    @SerializedName("id"                ) var id              : String?    = null,
    @SerializedName("service_id"                ) var service_id              : String?    = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("icon"       ) var icon      : String? = null,
    @SerializedName("active"     ) var active    : Int?    = null,
    @SerializedName("pivot"      ) var pivot     : Pivot?  = Pivot(), // pivot returns in order info re`uest

    var choosen: Boolean= false

)