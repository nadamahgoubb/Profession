package com.example.profession.domain


import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.CityParams
import com.example.profession.data.dataSource.Param.GetOrderParam
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.util.Constants


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

        }
        else {
            flow {
                emit( null)
            }as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
    }

}
