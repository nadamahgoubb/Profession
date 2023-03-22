package com.example.profession.base

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class BasePagingResponse<T> (

    @SerializedName("data") var data: DataPaging<T>? = null


    ) : BaseResponse(){
    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Parcelable> {
        override fun createFromParcel(parcel: Parcel): DevResponse<Parcelable> {
            return DevResponse(parcel)
        }

        override fun newArray(size: Int): Array<Parcelable?> {
            return arrayOfNulls(size)
        }
    }
    }


data class PagingCore<T>(@SerializedName("data") var dataObj: DataPaging<T>? = null
)
data class DataPaging<T> (

    @SerializedName("current_page"   ) var currentPage  : Int?             = null,
    @SerializedName("data"           ) var data         : ArrayList<T>  = arrayListOf(),
    @SerializedName("first_page_url" ) var firstPageUrl : String?          = null,
    @SerializedName("from"           ) var from         : Int?             = null,
    @SerializedName("last_page"      ) var lastPage     : Int?             = null,
    @SerializedName("last_page_url"  ) var lastPageUrl  : String?          = null,
     @SerializedName("next_page_url" ) var nextPageUrl  : String?          = null,
    @SerializedName("path"           ) var path         : String?          = null,
    @SerializedName("per_page"       ) var perPage      : Int?             = null,
    @SerializedName("prev_page_url"  ) var prevPageUrl  : String?          = null,
    @SerializedName("to"             ) var to           : Int?             = null,
    @SerializedName("total"          ) var total        : Int?             = null

)



