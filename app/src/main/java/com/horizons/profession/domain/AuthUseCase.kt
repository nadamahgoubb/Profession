package com.horizons.profession.domain


import com.horizons.profession.base.BaseUseCase
import com.horizons.profession.base.DevResponse
import com.horizons.profession.base.ErrorResponse
import com.horizons.profession.base.NetworkResponse
import com.horizons.profession.data.dataSource.Param.CityParams
 import com.horizons.profession.data.dataSource.Param.LoginParms
import com.horizons.profession.data.dataSource.Param.RegisterParams
import com.horizons.profession.data.dataSource.repositoy.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



@ViewModelScoped
class AuthUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<DevResponse<Any>, Any>() {

    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return if (params is LoginParms) {
            flow {
                params.let { repository.login(it) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }else if (params is RegisterParams) {
            flow {
                params.let { repository.register(it) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }else if (params is CityParams) {
            flow {
                params.let { repository.getCities(it) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }

        else {
            flow {
                emit( repository.getCountries())
            }as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
    }

}