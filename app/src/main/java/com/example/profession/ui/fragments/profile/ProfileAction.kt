package com.example.profession.ui.fragments.profile

 import com.example.profession.base.Action
 import com.example.profession.data.dataSource.Param.EditProfileParams
 import com.example.profession.data.dataSource.response.*


sealed class ProfileAction : Action {

    data class ShowLoading(val show: Boolean) : ProfileAction()
    data class ShowFailureMsg(val message: String?) : ProfileAction()

    data  class ShowProfile(val data: ProfileResponse) : ProfileAction()
    data  class ShowBalance(val msg: String) : ProfileAction()
    data  class ShowPhoneConfirmed(val data: ConfrmPhoneResponse) : ProfileAction()
    data  class ProfileUPdatedVaild(val data: EditProfileParams) : ProfileAction()
    data  class ShowUpdatesProfile(val message: String) : ProfileAction()
    data  class ChangedPassword(val message: String?) : ProfileAction()
    data  class DeleteAccount(val message: String?) : ProfileAction()
    data class ShowAllCities(var data: CitesResponse, val type: Int) : ProfileAction()
    data class ShowAllCountry(var data: CountriesResponse, val type: Int) : ProfileAction()
    data class ShowNotifactions(var data: NotificationResponse ) : ProfileAction()


}
