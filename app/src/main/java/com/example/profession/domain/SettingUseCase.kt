package com.example.profession.domain

 import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.ChangePasswordParam
 import com.example.profession.data.dataSource.Param.ComplainParams
 import com.example.profession.data.dataSource.Param.EditProfileParams
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.domain.GetProfileData.DELETE_ACCOUNT
import com.example.profession.domain.GetProfileData.GET_DATA
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class SettingUseCase @Inject constructor(private val repo: Repository  ) :
    BaseUseCase<DevResponse<Any>, Any>() {


    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return if ((params is ComplainParams)) {
            flow {
                params?.let { repo.complain(params) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        }  else {
            flow {
                emit(null)
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        }
    }
}






