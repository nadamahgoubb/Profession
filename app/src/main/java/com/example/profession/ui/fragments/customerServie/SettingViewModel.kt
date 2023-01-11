package com.example.profession.ui.fragments.customerServie

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.laundrydelivery.util.ext.isNull
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.Param.CityParams
import com.example.profession.data.dataSource.Param.ComplainParams
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.response.UserResponse

import com.example.profession.domain.AuthUseCase
import com.example.profession.domain.CitiesPagingUseCase
import com.example.profession.domain.CountriesPagingUseCase
import com.example.profession.domain.SettingUseCase
import com.example.profession.util.Extension
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel
@Inject constructor(app: Application, val usecase: SettingUseCase) :
    BaseViewModel<SettingAction>(app) {


    fun isValidParamsComplain(title: String?, content: String?): Boolean {

        return if (title.isNullOrBlank()) {
            produce(SettingAction.ShowFailureMsg(getString(R.string.please_enter_your_title)))
            false
        } else if (content.isNullOrBlank()) {
            produce(SettingAction.ShowFailureMsg(getString(R.string.msg_empty_content)))
            false
        } else {
            complain(title, content)
            true
        }

    }


    fun complain(title: String, content: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(SettingAction.ShowLoading(true))
            usecase.invoke(
                viewModelScope, ComplainParams(
                    PrefsHelper.getUserData()?.id, title, content
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(SettingAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(SettingAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        PrefsHelper.saveToken((res.data.data as UserResponse).token)
                        PrefsHelper.saveUserData(res.data.data as UserResponse)
                        produce(SettingAction.LoginSuccess(res.data.data as UserResponse))

                    }
                }
            }
        } else {
            produce(SettingAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

}

 



