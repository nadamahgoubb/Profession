package com.example.profession.domain

 import com.example.profession.base.BaseUseCase
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
  import com.example.profession.data.dataSource.Param.ComplainParams
 import com.example.profession.data.dataSource.Param.ContactUsParams
 import com.example.profession.data.dataSource.repositoy.Repository
 import com.example.profession.data.dataSource.response.GoalResponse
 import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class SettingUseCase @Inject constructor(private val repo: Repository  ) :
    BaseUseCase<DevResponse<Any>, Any>() {

   companion object Support{
       val GET_GOAL =1
    }


    override fun executeRemote(params: Any?): Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>> {
        return if ((params is ComplainParams)) {
            flow {
                params.let { repo.complain(params) }?.let { emit(it) }
            }
        } else if ((params is ContactUsParams)) {
            flow {
                params.let { repo.contactUs(params) }?.let { emit(it) }
            }
        }  else if ((params ?.equals(GET_GOAL) == true)) {
            flow {
                params.let { repo.getGoal() }?.let { emit(it) }
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        }
        else {
            flow {
                emit(repo.getTermsProvider())
            } as Flow<NetworkResponse<DevResponse<Any>, ErrorResponse>>
        }
    }
}







