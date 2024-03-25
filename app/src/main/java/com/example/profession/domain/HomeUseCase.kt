package com.example.profession.domain


import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.FcmParams
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.repositoy.Repository


import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

object GetHomeData {
    val Services = 1
    val Slider = 2

}

@ViewModelScoped
class HomeUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<DevResponse<Any>, Any>() {

    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return flow {
                emit(repository.getSlider())
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>

        }

    }


