package com.horizons.profession.ui.fragments.customerServie

 import com.horizons.profession.base.Action
 import com.horizons.profession.data.dataSource.response.GoalResponse

sealed class SettingAction : Action {

     data class ShowLoading(val show: Boolean) : SettingAction()
    data class ShowFailureMsg(val message: String?) : SettingAction()
    data class CompalinSucessed(val message: String?) : SettingAction()
    data class ContactSucessed(val message: String?) : SettingAction()
    data class ShowGoal(val data: GoalResponse?) : SettingAction()
    data class ShowTermsAndConditions(val data: GoalResponse?) : SettingAction()



}
