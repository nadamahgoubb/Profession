package com.example.profession.domain

 import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.ChangePasswordParam
import com.example.profession.data.dataSource.Param.EditProfileParams
 import com.example.profession.data.dataSource.Param.UpdateBalanceParam
 import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.domain.GetProfileData.DELETE_ACCOUNT
import com.example.profession.domain.GetProfileData.GET_DATA
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


object GetProfileData {
    val GET_DATA = 1
    val DELETE_ACCOUNT = 2

}


@ViewModelScoped
class ProfileUseCase @Inject constructor(private val repo: Repository  ) :
    BaseUseCase<DevResponse<Any>, Any>() {


    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return if ((params is EditProfileParams)) {
            flow {
                params.let { repo.updateProfile(params) }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        } else if (params is ChangePasswordParam) {
            flow {
                params.let { repo.changePassword(params) }?.let { emit(it) }
            }
        } else if (params?.equals(GET_DATA) == true) {
            flow {
                params.let { repo.getProfile() }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        } else if (params?.equals(DELETE_ACCOUNT) == true) {
            flow {
                params.let { repo.deleteAccount() }?.let { emit(it) }
            }
        }  else if (params is UpdateBalanceParam) {
            flow {
                params.let { repo.updateBalance(it) }?.let { emit(it) }
            }
        } else {
            flow {
                emit(repo.getNotifications())
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        }
    }
}







