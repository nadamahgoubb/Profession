package com.example.profession.ui.fragments.auth

import androidx.paging.PagingData
import com.example.profession.base.Action
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.UserResponse


/*
object LoadDataType {
    val loadDataFirstTime = 1
    val EditProfileLoad = 2

}*/

sealed class AuthAction() : Action {
    data class  LoginSuccess(val data: UserResponse): AuthAction()
    data class  RegisterationSuccess(val data: UserResponse)  : AuthAction()

     data class ShowLoading(val show: Boolean) : AuthAction()
    data class ShowFailureMsg(val message: String?) : AuthAction()
    data class ShowAllCities(var data: PagingData<CitesItemsResponse>) : AuthAction()
    data class ShowAllCountry(var data: PagingData<CitesItemsResponse>) : AuthAction()



}
