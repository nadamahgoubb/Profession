package com.example.profession.data.dataSource.Param


data class LoginParms(


    var phone: String = "",
      var password: String = ""

)

data class RegisterParams(
    var name: String,
    var phone: String,
    var email: String,
    var countryCode: String,
    var countryId: String? = null,
    var cityId: String? = null,
    var password: String,
    var lat: String,
    var lon: String,
    var mobile_id: String
)

