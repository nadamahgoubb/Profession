package com.example.profession.ui.fragments.customerServie

 import com.example.profession.base.Action

sealed class SettingAction() : Action {

     data class ShowLoading(val show: Boolean) : SettingAction()
    data class ShowFailureMsg(val message: String?) : SettingAction()
    data class CompalinSucessed(val message: String?) : SettingAction()



}
