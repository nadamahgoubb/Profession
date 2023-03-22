package com.example.profession.ui.fragments.order

import com.example.profession.base.Action
import com.example.profession.data.dataSource.response.CitesResponse
import com.example.profession.data.dataSource.response.CountriesResponse
import com.example.profession.data.dataSource.response.OrderdResponse

sealed class OrdersAction() : Action {
    data class  ShowOrders(val data: OrderdResponse): OrdersAction()
      data class ShowLoading(val show: Boolean) : OrdersAction()
    data class ShowFailureMsg(val message: String?) : OrdersAction()

}
