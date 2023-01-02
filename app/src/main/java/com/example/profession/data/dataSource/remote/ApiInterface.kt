package com.example.profession.data.dataSource.remote


import com.example.profession.base.BasePagingResponse
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.response.*
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


    @POST("cities_by_country_id")
    suspend fun getCities(
        @Field("country_id") countryId: String? = null,
        @Query("page") page: Int? = null,
    ): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse>
//

    @GET("countries")
    suspend fun getCountries(
        @Query("page") page: Int? = null,

    ): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse>

    @GET("user/services")
    suspend fun getServices(
        @Query("page") page: Int? = null,

    ): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse>

    @GET("user/slider")
    suspend fun getSlider(
        @Query("page") page: Int? = null,

        ): NetworkResponse<BasePagingResponse<SliderItemsResponse>, ErrorResponse>

   @FormUrlEncoded
   @POST("user/services/sub_service")
    suspend fun getSubServiceItemsResponse(
        @Query("page") page: Int? = null,
        @Field("service_id") service_id: String? = null,

        ): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse>



}