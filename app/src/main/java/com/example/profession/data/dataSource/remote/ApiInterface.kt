package com.example.profession.data.dataSource.remote


import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
 import com.example.profession.data.dataSource.response.UserResponse
import retrofit2.http.*
import javax.inject.Singleton

@Singleton

interface ApiInterface {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("phone") phone: String, @Field("password") password: String
    ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("country_code") countryCode: String,
        @Field("country_id") countryId: String? = null,
        @Field("city_id") cityId: String? = null,
        @Field("password") password: String,
        @Field("lat") lat: String,
        @Field("lon") lon: String,
        @Field("mobile_id") mobile_id: String,
        ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse>


    @GET("cities")
    suspend fun getCities(

    ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse>

}