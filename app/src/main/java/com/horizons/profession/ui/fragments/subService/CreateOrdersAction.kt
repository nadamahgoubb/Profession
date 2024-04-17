package com.horizons.profession.ui.fragments.subService

import com.horizons.profession.base.Action
import com.horizons.profession.data.dataSource.response.*


sealed class CreateOrdersAction : Action {

    data class ShowProviders(val data: ProvidersResponse) : CreateOrdersAction()
    data class ShowLoading(val show: Boolean) : CreateOrdersAction()
    data class ShowFailureMsg(val message: String?) : CreateOrdersAction()
     data class ShowTaxResponse(val data: TaxResponse?) : CreateOrdersAction()
    data class ShowProviderReview(val data: ReviewsResponse) : CreateOrdersAction()
    data class ShowNationalitesResponse(val data: NationalitiesResponse) : CreateOrdersAction()
 data    class ShowOrderSucess(val data: OrdersItem) : CreateOrdersAction()
}
