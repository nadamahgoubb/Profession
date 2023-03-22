package com.example.profession.ui.fragments.order

import android.app.Application
import androidx.lifecycle.viewModelScope
 import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.R
import com.example.profession.base.BaseViewModel
import com.example.profession.data.dataSource.Param.GetOrderParam
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.response.OrderdResponse
import com.example.profession.data.dataSource.response.UserResponse
import com.example.profession.domain.OrdersUseCase
import com.example.profession.util.NetworkConnectivity
import com.example.profession.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel
@Inject constructor(app: Application, val useCase: OrdersUseCase) :
    BaseViewModel<OrdersAction>(app) {


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
                         produce(OrdersAction.ShowOrders(res.data.data as OrderdResponse))

                    }
                }
            }
        } else {
            produce(OrdersAction.ShowFailureMsg(getString(R.string.no_internet)))
        }
    }
 }

 



