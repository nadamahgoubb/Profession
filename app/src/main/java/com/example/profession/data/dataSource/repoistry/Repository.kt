package com.example.profession.data.dataSource.repositoy


import com.example.profession.base.BasePagingResponse
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.LoginParms
import com.example.profession.data.dataSource.Param.RegisterParams
import com.example.profession.data.dataSource.remote.ApiInterface
import com.example.profession.data.dataSource.response.*
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiInterface) {


    suspend fun login(param: LoginParms): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.login(param.phone, param.password)
    }

    suspend fun register(
        param: RegisterParams,
    ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.register(
            param.name,
            param.phone,
            param.email,
            param.countryCode,
            param.countryId,
            param.cityId,
            param.password,
            param.lat,
            param.lon,
            param.mobile_id
        )


    }

    suspend fun getCities(
        page: Int, countryId: String
    ): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse> {
        return api.getCities(countryId, page)
    }

    suspend fun getCountries(page: Int): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse> {
        return api.getCountries()
    }

    suspend fun getServices(page: Int): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse> {
        return api.getServices()
    }

    suspend fun getSlider(page: Int): NetworkResponse<BasePagingResponse<SliderItemsResponse>, ErrorResponse> {
        return api.getSlider()
    }

    suspend fun getSubServiceItemsResponse(page: Int): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse> {
        return api.getSubServiceItemsResponse()
    }
}

