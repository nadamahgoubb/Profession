package com.horizons.profession.domain


import com.horizons.profession.base.BaseUseCase
import com.horizons.profession.base.DevResponse
import com.horizons.profession.base.ErrorResponse
import com.horizons.profession.base.NetworkResponse
import com.horizons.profession.data.dataSource.repositoy.Repository


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


