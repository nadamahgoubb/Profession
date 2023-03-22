package com.example.profession.data.dataSource.Param

import retrofit2.http.Field


data class GetProvidersParam(
    val service_id: String? = null,
    val lat: String? = null,
    val lon: String? = null,
)

data class GetProvidersReviewsParam(
    val provider_id: String? = null,
)

data class AddReviewsParam(
val provider_id: String? = null,
val  user_id: String? = null,
val order_id: String? = null,
val rate: String? = null,
val comment: String? = null,)

data class GetOrderParam(
    val order_status: String? = null,
)

data class CancelOrderParam(
   val order_id: String = "",
    val order_status: String = "",)
