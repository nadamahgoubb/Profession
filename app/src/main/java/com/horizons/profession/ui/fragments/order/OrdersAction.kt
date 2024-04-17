package com.horizons.profession.ui.fragments.order

import com.horizons.profession.base.Action
import com.horizons.profession.data.dataSource.response.OrderdResponse
import com.horizons.profession.data.dataSource.response.OrdersItem

sealed class OrdersAction : Action {
    data class  ShowOrders(val data: OrderdResponse, val state: String): OrdersAction()
    data class  ShowOrderDetails(val data: OrdersItem): OrdersAction()
      data class ShowLoading(val show: Boolean) : OrdersAction()
    data class ShowFailureMsg(val message: String?) : OrdersAction()
    data class ShowReviewAdded(val message: String?) : OrdersAction()
    data class ShowCanceledOrder(val message: String) : OrdersAction()
    data class ShowComplainedOrder(val message: String) : OrdersAction()
    data class ShowOrderPaid(val message: String) : OrdersAction()
}
