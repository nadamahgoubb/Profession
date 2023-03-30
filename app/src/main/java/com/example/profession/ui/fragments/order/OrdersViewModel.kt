package com.example.profession.ui.fragments.order

import android.app.Application
import androidx.lifecycle.viewModelScope
 import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.*
import com.example.profession.data.dataSource.response.OrderdResponse
import com.example.profession.data.dataSource.response.OrdersItem
 import com.example.profession.domain.OrdersUseCase
import com.example.profession.domain.ReviewsUseCase
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel
@Inject constructor(app: Application, val useCase: OrdersUseCase , val useCaseReview: ReviewsUseCase) :
    BaseViewModel<OrdersAction>(app) {

  var   orderId :String =""
    fun getOrders(state: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(OrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, GetOrderParam(
                   state
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(OrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(OrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                         produce(OrdersAction.ShowOrders(res.data.data as OrderdResponse, state))

                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun getOrderDetails(orderId: String) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {


            produce(OrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, OrderDetailsParam(
                    orderId
                )
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(OrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(OrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                         produce(OrdersAction.ShowOrderDetails(res.data.data as OrdersItem))

                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
    fun validateAddReview(
        provider_id: String?, user_id: String?, rate: Float, comment: String?
    ): Boolean {
        return if (rate== 0F) {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.empty_rate)))
            false
        } else if (comment.isNullOrBlank()) {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.empty_comment)))
            false
        } else {
            addReview(AddReviewsParam(provider_id, user_id, orderId, rate.toString(), comment))
            true
        }
    }

    fun addReview(
        params: AddReviewsParam
    ) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(OrdersAction.ShowLoading(true))
            useCaseReview.invoke(
                viewModelScope, params
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(OrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(OrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(OrdersAction.ShowReviewAdded(res.data.message as String))
                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
 fun cancelOrder(
        params: CancelOrderParam
    ) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(OrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, params
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(OrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(OrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(OrdersAction.ShowCanceledOrder(res.data.message as String))
                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }fun complainOrder(
        params: ComplainOrderParam
    ) {
        if (app?.let { it1 -> NetworkConnectivity.hasInternetConnection(it1) } == true) {

            produce(OrdersAction.ShowLoading(true))
            useCase.invoke(
                viewModelScope, params
            ) { res ->
                when (res) {
                    is Resource.Failure -> produce(OrdersAction.ShowFailureMsg(res.message.toString()))
                    is Resource.Progress -> produce(OrdersAction.ShowLoading(res.loading))
                    is Resource.Success -> {
                        produce(OrdersAction.ShowComplainedOrder(res.data.message as String))
                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }

}

 



