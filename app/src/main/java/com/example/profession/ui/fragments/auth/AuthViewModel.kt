package com.example.profession.ui.fragments.auth

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.laundrydelivery.util.ext.isNull
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.Param.CityParams
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.response.UserResponse

import com.example.profession.domain.AuthUseCase
import com.example.profession.domain.CitiesPagingUseCase
import com.example.profession.domain.CountriesPagingUseCase
import com.example.profession.util.Extension
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
 import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject constructor(app: Application, val authUserCase: AuthUseCase, val usecaseCity: CitiesPagingUseCase, val useCaseCountry: CountriesPagingUseCase) :
    BaseViewModel<AuthAction>(app) {
    var name: String? = null
    var email: String? = null
    var country_code: String? = null
    var phone: String? = null
    var password: String? = null
      var lat: Double? = null
    var lon: Double? = null
    var countryId: String? = null
    var cityId: String? = null



    fun isValidParams(phone: String?, pass: String?): Boolean {

        return if (phone.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.please_enter_your_phone)))
            false
        }  else if (pass.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.msg_empty_password)))
            false
        } else {
login(phone,pass)
            true
        }

    }


    fun login(phone: String, pass: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(AuthAction.ShowLoading(true))
            authUserCase.invoke(
                viewModelScope, LoginParms(
                    phone,
                    pass
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(AuthAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(AuthAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                    PrefsHelper.saveToken((res.data.data  as UserResponse ).token)
                        PrefsHelper.saveUserData(res.data.data  as UserResponse )
                        produce(AuthAction.LoginSuccess(res.data.data  as UserResponse ))

                    }
                }
            }
        } else {
            produce(AuthAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
 /*   param.name,
    param. phone,
    param. email,
    param.countryCode,
    param.   countryId,
    param.    cityId,
    param.      password,
    param.   lat,
    param.    lon,
    param.  mobile_id*/
    fun validateRegisteration(
        name: String,
        phone: String,
        email: String,
        country_code: String?,
        countryId:String,
        cityId:String,
        pass: String,
        lat: Double?,
        lon: Double?,

 ): Boolean {
        return  if (name.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.empty_user_name)))
            false
        }
     else   if (phone.isNullOrBlank()) {
         produce(AuthAction.ShowFailureMsg(getString(R.string.empty_num)))
         false
     }
     else if (email.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.msg_empty_email)))
            false
        } else if (!Extension.isEmailValid(email)) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.msg_invalide_email)))
            false
        }   else if (country_code.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.empty_num_code)))
            false
        }
        else if (countryId.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.empty_country_id)))
            false
        }
        else if (cityId.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.empty_cityid)))
            false
        }
        else if (pass.isNullOrBlank()) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.msg_empty_password)))
            false
        }   else if  (lat.isNull() || lon?.equals(0) == true) {
            produce(AuthAction.ShowFailureMsg(getString(R.string.enter_your_location)))
            false
        }  else {

        this.name = name
            this.phone = phone
            this.email = email
            this.country_code = country_code
            this.countryId = countryId
            this.cityId = cityId
            this.password = pass
             this.lon = lon
            this.lat = lat
register(name,phone,email, country_code, countryId,cityId,pass, lat, lon, "0")
            true

        }
    }

    fun register(
        name: String,
        phone: String,
        email: String,
        country_code: String,
        countryId:String,
        cityId:String,
        pass: String,
        lat: Double?,
        lon: Double?,
        mobile_id: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            produce(AuthAction.ShowLoading(true))

            viewModelScope.launch {
                var res = authUserCase.invoke(
                    viewModelScope, RegisterParams(
                        name,phone,email, country_code, countryId,cityId,pass,
                        lat.toString(), lon.toString(), mobile_id
                    )
                )

                { res ->
                    when (res) {
                        is Resource.Failure -> produce(AuthAction.ShowFailureMsg(res.message.toString()))
                        is Resource.Progress -> produce(AuthAction.ShowLoading(res.loading))
                        is Resource.Success -> {
                             PrefsHelper.saveToken((res.data.data as UserResponse).token)
                            PrefsHelper.saveUserData(res.data.data  as UserResponse )
                             produce(AuthAction.RegisterationSuccess(res.data.data  as UserResponse ))

                        }
                        }
                    }
                }
            }
       else {
            produce(AuthAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

    fun getAllCitiesByCountryId(country_id:String){

        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            usecaseCity.invoke(
                viewModelScope, CityParams(country_id)
            ) { data ->
                viewModelScope.launch {
                    produce(AuthAction.ShowAllCities(data))
                }
            }
        }else {
            produce(AuthAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun getAllCountry(){

        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            useCaseCountry.invoke(
                viewModelScope, PagingParams()
            ) { data ->
                viewModelScope.launch {
                    produce(AuthAction.ShowAllCountry(data))
                }
            }
        }else {
            produce(AuthAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
}

 



