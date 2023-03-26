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
import com.example.profession.domain.ReviewsUseCase
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.util.Extension
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateOrdersViewModel
@Inject constructor(
    app: Application, val useCase: CreateOrdersUseCase, val useCaseReview: ReviewsUseCase
) : BaseViewModel<CreateOrdersAction>(app) {
    var selectedSubservice: ArrayList<SubServiceItemsResponse> = arrayListOf()
    var selectedProviders: ArrayList<Providers> = arrayListOf()
    var lat: Double? = null
    var long: Double? = null
    var loc: Location? = null
    var serviceId: String? = null
    var address: AddressParams? = null
    var tax: Double? = null
    var hoursCount = 1
    var hourToVisit = ""
    var mintueToVisit = "00"
    var am = "am"

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
                viewModelScope, GetProvidersReviewsParam(provider_id)
            ) { res ->
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

    fun validateAddReview(
        provider_id: String, user_id: String, order_id: String, rate: String?, comment: String?
    ): Boolean {
        return if (rate.isNullOrBlank()) {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.empty_rate)))
            false
        } else if (comment.isNullOrBlank()) {
            produce(CreateOrdersAction.ShowFailureMsg(getString(R.string.empty_comment)))
            false
        } else {
            addReview(AddReviewsParam(provider_id, user_id, order_id, rate, comment))
            true
        }
    }

    fun addReview(
        params: AddReviewsParam
    ) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(CreateOrdersAction.ShowLoading(true))
            useCaseReview.invoke(
                viewModelScope, params
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(CreateOrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(CreateOrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(CreateOrdersAction.ShowReviewAdded(res.data.message as String))
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
}

 



