package com.example.profession.data.dataSource.repositoy


import com.example.profession.base.BasePagingResponse
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.*
import com.example.profession.data.dataSource.remote.ApiInterface
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.*
import com.example.profession.util.FileManager.toMultiPart
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
        return api.getCountries(page)
    }

    suspend fun getServices(page: Int): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse> {
        return api.getServices(page)
    }

    suspend fun getSlider(page: Int): NetworkResponse<BasePagingResponse<SliderItemsResponse>, ErrorResponse> {
        return api.getSlider(page)
    }

    suspend fun getSubServiceItemsResponse(page: Int , serviceId:String): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse> {
        return api.getSubServiceItemsResponse(page  , serviceId)
    }


    suspend fun getProfile(): NetworkResponse<DevResponse<ProfileResponse>, ErrorResponse> {
        return api.getProfile(
        //    "Bearer" +PrefsHelper.getToken()
        )
    }

    suspend fun updateProfile(param: EditProfileParams): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.updateProfile( param.toMap(),           param.photo?.let {  param.photo?.toMultiPart("photo") },
        )
    }
    suspend fun changePassword(param: ChangePasswordParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.changePassword( param.oldPassword, param.newPassword)
    }
    suspend fun deleteAccount(): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.deleteAccount( )
    }
}

