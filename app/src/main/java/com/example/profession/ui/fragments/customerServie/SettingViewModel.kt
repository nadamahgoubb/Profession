package com.example.profession.ui.fragments.customerServie

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.ComplainParams
import com.example.profession.data.dataSource.Param.ContactUsParams
import com.example.profession.data.dataSource.response.UserResponse

import com.example.profession.domain.SettingUseCase
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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
                          produce(SettingAction.CompalinSucessed(res.data.message as String))

                    }
                }
            }
        } else {
            produce(SettingAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun contactUs(  content: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(SettingAction.ShowLoading(true))
            usecase.invoke(
                viewModelScope, ContactUsParams(
                  0,  content //0 for user
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(SettingAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(SettingAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                          produce(SettingAction.ContactSucessed(res.data.message as String))

                    }
                }
            }
        } else {
            produce(SettingAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

}

 



