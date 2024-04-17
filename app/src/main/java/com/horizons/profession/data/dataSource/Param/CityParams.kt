package com.horizons.profession.data.dataSource.Param


data class CityParams(var countryId:String)
data class AddressParams(var address:String?,var country:String?, val city:String? ,val district:String? ,val  street:String?,val lat :String? , val lon:String? )
