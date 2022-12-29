package com.example.profession.data.dataSource.repositoy



import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.remote.ApiInterface
import com.example.profession.data.dataSource.response.UserResponse
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiInterface) {


    suspend fun login(param: LoginParms): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.login(param.phone, param.password)
    }

    suspend fun register(
        param: RegisterParams,
    ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.register(param.name,
            param. phone,
            param. email,
            param.countryCode,
            param.   countryId,
            param.    cityId,
            param.      password,
            param.   lat,
            param.    lon,
            param.  mobile_id )


    }
}

