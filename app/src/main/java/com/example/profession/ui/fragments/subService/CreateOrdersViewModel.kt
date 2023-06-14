package com.example.profession.ui.fragments.subService

import android.app.Application
import android.location.Location
import androidx.lifecycle.viewModelScope
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.*
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.*
import com.example.profession.domain.CreateOrdersUseCase
import com.example.profession.domain.ReviewsUseCase
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import com.example.profession.util.Utils.ArabicToEnglish
import com.example.profession.util.Utils.englishNumberToArabicNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateOrdersViewModel
@Inject constructor(
    app: Application, val useCase: CreateOrdersUseCase, val useCaseReview: ReviewsUseCase
) : BaseViewModel<CreateOrdersAction>(app) {
    var paymentType: Int? = -1
    var selectedSubservice: ArrayList<SubServiceItemsResponse> = arrayListOf()
    var selectedProviders: ArrayList<Providers> = arrayListOf()
    var allProviders: ArrayList<Providers> = arrayListOf()
    var lat: Double? = null
    var long: Double? = null
    var loc: Location? = null
    var serviceId: String? = null
    var address: AddressParams? = null
    var tax: Double? = null
    var hoursCount = 1
    var hourToVisit = ""
    var mintueToVisit = "00"
    var current = ""
    var am = "ص"

    fun getProviders() {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, GetProvidersParam(serviceId, lat.toString(), long.toString())
            ) { res ->
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

    fun getProviderReview(provider_id: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCaseReview.invoke(
                viewModelScope, GetProvidersReviewsParam(provider_id))
            { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(CreateOrdersAction.ShowProviderReview(res.data.data as ReviewsResponse))
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
                viewModelScope
            ) { res ->
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
    fun getNationalities() {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, CreateOrdersUseCase.nationalities
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(CreateOrdersAction.ShowNationalitesResponse(res.data.data as NationalitiesResponse))
                    }
                }
            }
        } else {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

    fun submitOrder(
        selectedServiceIds: ArrayList<String>,
        providers: ArrayList<ProvidersCreateOrderParams>,
        notes: String
    ) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, CreateOrderParams(
                     PrefsHelper.getUserData()?.id,
                    paymentType,
                    lat.toString(),
                    long.toString(),
                    address?.address.toString(),
                    englishNumberToArabicNumber( current),
                    hourToVisit + ":" + mintueToVisit + " " + am,
                    hoursCount,
                    notes,
                    PrefsHelper.getUserData()?.phone,
                    PrefsHelper.getUserData()?.countryCode,
                    selectedServiceIds,
                    providers
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(CreateOrdersAction.ShowOrderSucess(res.data.data as OrdersItem))
                    }
                }
            }
        } else {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
}

 



