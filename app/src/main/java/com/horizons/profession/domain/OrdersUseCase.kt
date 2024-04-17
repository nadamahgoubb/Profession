package com.horizons.profession.domain


import com.horizons.profession.base.BaseUseCase
import com.horizons.profession.base.DevResponse
import com.horizons.profession.base.ErrorResponse
import com.horizons.profession.base.NetworkResponse
import com.horizons.profession.data.dataSource.Param.*
import com.horizons.profession.data.dataSource.repositoy.Repository


import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



@ViewModelScoped
class OrdersUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<DevResponse<Any>, Any>() {

    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return if (params  is GetOrderParam  ) {
            flow {
                params.let { repository.getOrders(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        } else if  (params  is OrderDetailsParam) {
            flow {
                params.let { repository.getOrderById(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
 else if  (params  is CancelOrderParam) {
            flow {
                params.let { repository.cancelOrder(params ) }?.let { emit(it) }
            }

        } else if  (params  is ComplainOrderParam) {
            flow {
                params.let { repository.complainOrder(params ) }?.let { emit(it) }
            }

        }
else if  (params  is PayOrderParam) {
            flow {
                params.let { repository.payOrderWithVisa(params ) }?.let { emit(it) }
            }

        }

        else {
            flow {
                emit( null)
            }as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
    }

}