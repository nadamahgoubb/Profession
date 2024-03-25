package com.example.profession.domain


import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
 import com.example.profession.data.dataSource.Param.ForgetPasswordParms
import com.example.profession.data.dataSource.Param.confirmPhoneParms
import com.example.profession.data.dataSource.repositoy.Repository


import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



@ViewModelScoped
class ForgetPassUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<DevResponse<Any>, Any>() {

    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return   if (params is confirmPhoneParms) {
            flow {
                params.let { repository.confirmPhone(it) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }else if (params is ForgetPasswordParms) {
            flow {
                params.let { repository.forgetPassword(it) }?.let { emit(it) }
            }

        }

        else {
            flow {
                emit( null)
            }as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }
    }

}
