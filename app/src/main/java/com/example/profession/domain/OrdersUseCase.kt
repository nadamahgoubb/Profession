package com.example.profession.domain


import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.*
import com.example.profession.data.dataSource.repositoy.Repository


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
                params?.let { repository.getOrders(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        } else if  (params  is OrderDetailsParam) {
            flow {
                params?.let { repository.getOrderById(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
 else if  (params  is CancelOrderParam) {
            flow {
                params?.let { repository.cancelOrder(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        } else if  (params  is ComplainOrderParam) {
            flow {
                params?.let { repository.complainOrder(params ) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }

        else {
            flow {
                emit( null)
            }as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
    }

}
