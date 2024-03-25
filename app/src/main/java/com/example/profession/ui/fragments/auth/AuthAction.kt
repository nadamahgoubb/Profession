package com.example.profession.ui.fragments.auth

import com.example.profession.base.Action
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.response.CitesResponse
import com.example.profession.data.dataSource.response.ConfrmPhoneResponse
import com.example.profession.data.dataSource.response.CountriesResponse
import com.example.profession.data.dataSource.response.UserResponse


/*
object LoadDataType {
    val loadDataFirstTime = 1
    val EditProfileLoad = 2

}*/

sealed class AuthAction : Action {
    data class  LoginSuccess(val data: UserResponse): AuthAction()
    data class  RegisterationSuccess(val data: UserResponse)  : AuthAction()
    data class  ShowRegisterVaildationSucess(val data: RegisterParams)  : AuthAction()

     data class ShowLoading(val show: Boolean) : AuthAction()
    data class ShowFailureMsg(val message: String?) : AuthAction()
    data class ShowForgetPassword(val message: String?) : AuthAction()
    data class ShowPhoneConfirmed (val data: ConfrmPhoneResponse?) : AuthAction()
    data class ShowAllCities(var data: CitesResponse) : AuthAction()
    data class ShowAllCountry(var data: CountriesResponse) : AuthAction()



}
