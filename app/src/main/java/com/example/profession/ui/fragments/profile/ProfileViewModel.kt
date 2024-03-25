package com.example.profession.ui.fragments.profile

import android.app.Application
import androidx.lifecycle.viewModelScope
 import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.ChangePasswordParam
import com.example.profession.data.dataSource.Param.EditProfileParams
import com.example.profession.data.dataSource.response.ProfileResponse
import com.example.profession.data.dataSource.response.UserResponse
import com.example.profession.domain.GetProfileData
import com.example.profession.domain.ProfileUseCase
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import com.example.profession.R
import com.example.profession.data.dataSource.Param.CityParams
import com.example.profession.data.dataSource.Param.UpdateBalanceParam
import com.example.profession.data.dataSource.Param.confirmPhoneParms
import com.example.profession.data.dataSource.response.CitesResponse
import com.example.profession.data.dataSource.response.ConfrmPhoneResponse
import com.example.profession.data.dataSource.response.CountriesResponse
import com.example.profession.data.dataSource.response.NotificationResponse
import com.example.profession.domain.AuthUseCase
import com.example.profession.domain.ForgetPassUseCase
import com.example.profession.util.Extension
import com.example.profession.util.ext.isNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel
@Inject constructor(app: Application, var usecase: ProfileUseCase, var usecaseAuth: AuthUseCase , var usecaseCheckPhone: ForgetPassUseCase) :
    BaseViewModel<ProfileAction>(app) {
var phone:String?= null
var country_code:String?= null
var data:EditProfileParams?= null
    companion object{
        val getCurrentCountryName =1
        val getAllCountries =2
    }
    fun getProfile() {
        produce(ProfileAction.ShowLoading(true))

        viewModelScope.launch {
            var res = usecase.invoke(viewModelScope, GetProfileData.GET_DATA) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        ((res.data?.data) as ProfileResponse).let {
                            produce(
                                ProfileAction.ShowProfile(
                                    it,
                                )
                            )
                        }
                    }
                }

            }

        }
    }
    fun updateBalance (param: UpdateBalanceParam) {
        produce(ProfileAction.ShowLoading(true))

        viewModelScope.launch {
            var res = usecase.invoke(viewModelScope, param) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> { produce(ProfileAction.ShowBalance(res.data?.message.toString()))
                        }
                    }
                }

            }

        }

    fun deleteAccount() {

        produce(ProfileAction.ShowLoading(true))

        viewModelScope.launch {
            var res = usecase.invoke(viewModelScope, GetProfileData.DELETE_ACCOUNT) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                             produce(
                                ProfileAction.DeleteAccount(res.data.message
                                )
                            )

                    }
                }

            }

        }
    }
    fun validateUpdateProfile(
        name: String,
        phone: String,
        email: String,
        country_code: String?,
        countryId:String,
        cityId:String,
         lat: Double?,
        lon: Double?,
        photo: File?,
        address: String,

        ): Boolean {
        return  if (name.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.empty_user_name)))
            false
        }
        else   if (phone.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.empty_num)))
            false
        }
        else if (email.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.msg_empty_email)))
            false
        } else if (!Extension.isEmailValid(email)) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.msg_invalide_email)))
            false
        }   else if (country_code.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.empty_num_code)))
            false
        }
        else if (countryId.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.empty_country_id)))
            false
        }
        else if (cityId.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.empty_cityid)))
            false
        }
         else if  (lat.isNull() || lon?.equals(0) == true||address.isNullOrEmpty()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.enter_your_location)))
            false
        }  else {
this.data = EditProfileParams(name,phone,country_code,email,  countryId,cityId, lat.toString(), lon.toString(), "0", photo ,address)
            produce(
                ProfileAction.ProfileUPdatedVaild (this.data!!))
           true

        }
    }


    fun checkPhone(
        country_code: String, phone: String
    ) {
        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(ProfileAction.ShowLoading(true))
            usecaseCheckPhone.invoke(
                viewModelScope, confirmPhoneParms(phone, country_code)
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        this.phone = phone
                        this.country_code = country_code
                        produce(ProfileAction.ShowPhoneConfirmed(res.data.data as ConfrmPhoneResponse))

                    }
                }
            }
        } else {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
 


fun updateProfile (param: EditProfileParams) {
            viewModelScope.launch {
                var res = usecase.invoke(viewModelScope, param) { res ->
                    when (res) {
                        is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message))
                        is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                        is Resource.Success -> {
                            produce(ProfileAction.ShowUpdatesProfile(res.data.message))
                            PrefsHelper.saveUserData(res.data.data as UserResponse)

                        }
                    }
                }
            }
        }


    fun isValidParamsChangePass(pass: String, newpass: String, confirmpass: String) {
        if (pass.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.msg_empty_password)))
            false
        } else if (newpass.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.msg_empty_new_password)))
            false

        }
        else if (confirmpass.isNullOrBlank()) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.msg_empty_confirm_new_password)))
            false

        }else if (!confirmpass.equals(newpass)) {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.password_not_matching)))
            false

        }
        else
            changePass(ChangePasswordParam(pass, newpass ))


    }
    fun changePass(param: ChangePasswordParam) {
        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(ProfileAction.ShowLoading(true))
            usecase.invoke(
                viewModelScope, param
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(
                            ProfileAction.ChangedPassword(
                                res.data.message
                            )) }
                }
            }
        } else {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun getAllCitiesByCountryId(country_id: String, type :Int) {


        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(ProfileAction.ShowLoading(true))
            usecaseAuth.invoke(
                viewModelScope, CityParams(country_id)
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(ProfileAction.ShowAllCities(res.data.data as CitesResponse, type))

                    }
                }
            }
        } else {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

    fun getAllCountry(type :Int) {
        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(ProfileAction.ShowLoading(true))
            usecaseAuth.invoke(
                viewModelScope
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(ProfileAction.ShowAllCountry(res.data.data as CountriesResponse, type))

                    }
                }
            }
        } else {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun getNotifaction(   ) {
        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(ProfileAction.ShowLoading(true))
            usecase.invoke(
                viewModelScope
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(ProfileAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(ProfileAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(ProfileAction.ShowNotifactions(res.data.data as NotificationResponse))

                    }
                }
            }
        } else {
            produce(ProfileAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
}

