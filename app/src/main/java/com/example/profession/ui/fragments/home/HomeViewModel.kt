package com.example.profession.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.Param.FcmParams
import com.example.profession.data.dataSource.Param.SubServicesParams
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.ProfileResponse
import com.example.profession.data.dataSource.response.SliderResponse
import com.example.profession.domain.*
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import com.example.profession.ui.fragments.profile.ProfileAction
import com.example.profession.util.fcm.FcmParam
import com.example.profession.util.fcm.FcmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    var useCase: HomeUseCase,
    var usecaseService: ServicesPagingUseCase,
    var useCaseFcm: FcmUseCase,
    var usecaseSubService: SubServicesPagingUseCase
) : BaseViewModel<HomeAction>(app) {
    init {

        updateFcmToken()
    }

    fun getAllServices() {

        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            usecaseService.invoke(
                viewModelScope, PagingParams()
            ) { data ->
                viewModelScope.launch {
                    produce(HomeAction.ShowService(data))
                }
            }
        } else {
            produce(HomeAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

    /*
        fun getDAllServices() {
            produce(HomeAction.ShowLoading(true))

            viewModelScope.launch {
                var res = useCase.invoke(viewModelScope, GetHomeData.Services) { res ->
                    when (res) {
                        is Resource.Failure -> produce(HomeAction.ShowFailureMsg(res.message))
                        is Resource.Progress -> produce(HomeAction.ShowLoading(res.loading))
                        is Resource.Success -> {
                       *//*     ((res?.data?.data) as ProfileResponse).let {
                                produce(
                                    HomeAction.ShowService(
                                        it,
                                    )
                                )
                            }*//*

                    }
                }

            }

        }
    }
*/

    fun getAllSlider() {
        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            produce(HomeAction.ShowLoading(true))


            viewModelScope.launch {
                var res = useCase.invoke(viewModelScope, GetHomeData.Slider) { res ->
                    when (res) {
                        is Resource.Failure -> produce(HomeAction.ShowFailureMsg(res.message))
                        is Resource.Progress -> produce(HomeAction.ShowLoading(res.loading))
                        is Resource.Success -> {
                            produce(HomeAction.ShowSlider(res.data.data as SliderResponse))/*     ((res?.data?.data) as ProfileResponse).let {
                                                        produce(
                                                            HomeAction.ShowService(
                                                                it,
                                                            )
                                                        )
                                                    }*/
                        }
                    }

                }

            }
        } else {
            produce(HomeAction.ShowFailureMsg(getString(R.string.no_internet)))

        }

    }

    fun updateFcmToken() {
        if (PrefsHelper.getFcmToken().isNullOrEmpty()) useCaseFcm.generateFcmToken()
        else useCaseFcm.sendFcmTokenToServer(FcmParam(PrefsHelper.getFcmToken()))   }

    fun getSubService(serviceId: String) {

        if (app.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            usecaseSubService.invoke(
                viewModelScope, SubServicesParams(serviceId)
            ) { data ->
                viewModelScope.launch {
                    produce(HomeAction.ShowSubService(data))
                }
            }
        } else {
            produce(HomeAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

}



