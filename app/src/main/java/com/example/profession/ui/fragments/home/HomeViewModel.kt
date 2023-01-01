package com.example.profession.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.base.PagingParams
import com.example.profession.domain.ServicesPagingUseCase
import com.example.profession.domain.SliderPagingUseCase
import com.example.profession.domain.SubServicesPagingUseCase
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(app: Application , var usecaseService:ServicesPagingUseCase,   var usecaseSubService:SubServicesPagingUseCase, var usecaseSlider:SliderPagingUseCase) :
    BaseViewModel<HomeAction>(app) {
    fun getAllServices(){

        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            usecaseService.invoke(
                viewModelScope, PagingParams()
            ) { data ->
                viewModelScope.launch {
                    produce(HomeAction.ShowService(data))
                }
            }
        }else {
            produce(HomeAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

    fun getAllSlider(){

        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {
            usecaseSlider.invoke(
                viewModelScope, PagingParams()
            ) { data ->
                viewModelScope.launch {
                    produce(HomeAction.ShowSlider(data))
                }
            }
        }else {
            produce(HomeAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

}



