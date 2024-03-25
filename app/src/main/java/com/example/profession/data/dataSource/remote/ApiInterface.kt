package com.example.profession.data.dataSource.remote


import com.example.profession.base.BasePagingResponse
import com.example.profession.base.DevResponse
import com.example.profession.base.ErrorResponse
import com.example.profession.base.NetworkResponse
import com.example.profession.data.dataSource.Param.CreateOrderParams
import com.example.profession.data.dataSource.Param.GetProvidersReviewsParam
import com.example.profession.data.dataSource.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import javax.inject.Singleton
import com.example.profession.data.dataSource.response.SliderResponse
import com.example.profession.util.FileManager.toMultiPart

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
        @Field("address") address: String,
    ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse>


    @POST("cities_by_country_id")
    @FormUrlEncoded
    suspend fun getCities(
        @Field("country_id") countryId: String? = null,
    ): NetworkResponse<DevResponse<CitesResponse>, ErrorResponse>
  @POST("user/forgot-password")
    @FormUrlEncoded
    suspend fun forgetPassword(
        @Field("phone") phone: String ,
        @Field("country_code") country_code: String ,
        @Field("password") password: String  ,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>


    @GET("countries")
    suspend fun getCountries(): NetworkResponse<DevResponse<CountriesResponse>, ErrorResponse>

    @GET("user/services")
    suspend fun getServices(
        @Query("page") page: Int? = null,

        ): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/services/sub_service")
    suspend fun getSubServiceItemsResponse(
        @Query("page") page: Int? = null,
        @Field("service_id") service_id: String? = null,

        ): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse>

    @GET("user/slider")
    suspend fun getSlider(

    ): NetworkResponse<DevResponse<SliderResponse>, ErrorResponse>

    @GET("user/profile")
    suspend fun getProfile(
     ): NetworkResponse<DevResponse<ProfileResponse>, ErrorResponse>


    @Multipart
    @JvmSuppressWildcards
    @POST("user/profile/update")
    suspend fun updateProfile(
        @PartMap updateMap: Map<String, RequestBody>,
        @Part image: MultipartBody.Part?     ): NetworkResponse<DevResponse<UserResponse>, ErrorResponse>


    @FormUrlEncoded
    @POST("user/profile/change_password")
    suspend fun changePassword(
        @Field("old_password") old_password: String? = null,
        @Field("new_password") new_password: String? = null,

        ): NetworkResponse<DevResponse<Any>, ErrorResponse>

     @POST("user/profile/delete")
    suspend fun deleteAccount(
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/complaint")
    suspend fun complain(
        @Field("user_id") user_id: String? = null,
        @Field("title") title: String? = null,
        @Field("content") content: String? = null,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>
 @FormUrlEncoded
    @POST("user/contact_us")
    suspend fun contactUs(
        @Field("app_type") app_type: Int? = null,
         @Field("content") content: String? = null,
         @Field("phone") phone: String? = null,
         @Field("country_code") country_code: String? = null,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>

    @GET("user/goal")
    suspend fun getGoal(
    ): NetworkResponse<DevResponse<GoalResponse>, ErrorResponse>

      @GET("user/profile/notifications")
    suspend fun getNotifications(
    ): NetworkResponse<DevResponse<NotificationResponse>, ErrorResponse>

    @GET("user/terms_user")
    suspend fun getTermsProvider(
    ): NetworkResponse<DevResponse<GoalResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/get_providers")
    suspend fun getProviders(
        @Field("service_id") service_id: String? = null,
        @Field("lat") lat: String? = null,
        @Field("lon") lon: String? = null,
    ): NetworkResponse<DevResponse<ProvidersResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/get_reviews")
    suspend fun getReviews(
        @Field("provider_id") provider_id: String? = null,

    ): NetworkResponse<DevResponse<ReviewsResponse>, ErrorResponse>
     @GET("tax")
    suspend fun getTax(
    ): NetworkResponse<DevResponse<TaxResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/get_orders")
    suspend fun getOrders(
        @Field("order_status") order_status: String? = null,
    ): NetworkResponse<DevResponse<OrderdResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/cancel_order")
    suspend fun cancelOrder(
        @Field("order_id") order_id: String? = null,
        @Field("order_status") order_status: String? = null,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>
  @FormUrlEncoded
    @POST("user/complaint_order")
    suspend fun complainOrder(
        @Field("order_id") order_id: String? = null,
        @Field("complaint") complaint: String? = null,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/add_review")
    suspend fun addReview(
        @Field("provider_id") provider_id: String? = null,
        @Field("user_id") user_id: String? = null,
        @Field("order_id") order_id: String? = null,
        @Field("rate") rate: String? = null,
        @Field("comment") comment: String? = null,
    ): NetworkResponse<DevResponse<Any>, ErrorResponse>

    @POST("user/create_orders")
    suspend fun createOrder(
        @Body param: CreateOrderParams? = null,
    ): NetworkResponse<DevResponse<OrdersItem>, ErrorResponse>
    @FormUrlEncoded
    @POST("user/confirm-phone")
    suspend fun confirmPhone(
        @Field("phone") phone: String? = null,
        @Field("country_code") country_code: String? = null
    ): NetworkResponse<DevResponse<ConfrmPhoneResponse>, ErrorResponse>

    @GET("nationalities")
    suspend fun getNationalities(
    ): NetworkResponse<DevResponse<NationalitiesResponse>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/update_fcm_token")
    suspend fun updateFcm(
        @Field("fcm_token") fcm_token: String? = null,
        @Field("mobile_id") mobile_id: Int? = 0,
        ): NetworkResponse<DevResponse<Any>, ErrorResponse>

    @FormUrlEncoded
    @POST("user/get_order_by_id")
    suspend fun getOrderById(
         @Field("order_id") order_id: String
        ): NetworkResponse<DevResponse<OrdersItem>, ErrorResponse>
    @FormUrlEncoded
    @POST("user/pay_order_by_visa")
    suspend fun payOrderWithVisa(
         @Field("order_id") order_id: String,
         @Field("trans_ref") trans_ref: String,
         @Field("pay_token") pay_token: String,
        ): NetworkResponse<DevResponse<Any>, ErrorResponse>

   @FormUrlEncoded
    @POST("user/profile/update_balance")
    suspend fun updateBalance(
         @Field("balance") balance: String,
         @Field("trans_ref") trans_ref: String,
         @Field("pay_token") pay_token: String,
        ): NetworkResponse<DevResponse<Any>, ErrorResponse>

}