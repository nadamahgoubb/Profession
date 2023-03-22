package com.example.profession.ui.fragments.subService

import android.app.Application
import android.location.Location
import androidx.lifecycle.viewModelScope
 import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.*
import com.example.profession.data.dataSource.response.*
import com.example.profession.domain.CreateOrdersUseCase
 import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateOrdersViewModel
@Inject constructor(app: Application, val useCase: CreateOrdersUseCase) :
    BaseViewModel<CreateOrdersAction>(app) {
    var selectedSubservice: ArrayList<SubServiceItemsResponse> = arrayListOf()
    var selectedProviders: ArrayList<Providers> = arrayListOf()
   var lat: Double?= null
   var long: Double?= null
   var loc: Location? = null
   var serviceId: String? = null
   var address: AddressParams? = null


    fun getProviders() {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, GetProvidersParam(serviceId, lat.toString(), long.toString())) { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                         produce(CreateOrdersAction.ShowProviders(res.data.data as ProvidersResponse))
                    }
                }
            }
        } else {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun getTaxes() {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope  ) { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                         produce(CreateOrdersAction.ShowTaxResponse(res.data.data as TaxResponse))
                    }
                }
            }
        } else {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
 }

 



