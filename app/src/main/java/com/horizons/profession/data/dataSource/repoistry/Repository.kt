package com.horizons.profession.data.dataSource.repositoy


import com.horizons.profession.base.BasePagingResponse
import com.horizons.profession.base.DevResponse
import com.horizons.profession.base.ErrorResponse
import com.horizons.profession.base.NetworkResponse
import com.horizons.profession.data.dataSource.Param.*
import com.horizons.profession.data.dataSource.remote.ApiInterface
import com.horizons.profession.data.dataSource.response.*
import com.horizons.profession.util.FileManager.toMultiPart
import javax.inject.Inject
import com.horizons.profession.data.dataSource.response.SliderResponse
import com.horizons.profession.util.fcm.FcmParam


class Repository @Inject constructor(private val api: ApiInterface) {


    suspend fun login(param: LoginParms): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.login(param.country_code,param.phone, param.password)
    }
    suspend fun confirmPhone( params: confirmPhoneParms)=   api.confirmPhone( params.phone, params.country_code)

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
            param.mobile_id,
            param.address
        )


    }

    suspend fun getCities(params: CityParams): NetworkResponse<DevResponse<CitesResponse>, ErrorResponse> {
        return api.getCities(params.countryId)
    }

    suspend fun forgetPassword(params: ForgetPasswordParms): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.forgetPassword(params.phone, params.country_code, params.password)
    }

    suspend fun getCountries(): NetworkResponse<DevResponse<CountriesResponse>, ErrorResponse> {
        return api.getCountries()
    }

    suspend fun getServices(page: Int): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse> {
        return api.getServices(page)
    }

    suspend fun getSlider(): NetworkResponse<DevResponse<SliderResponse>, ErrorResponse> {
        return api.getSlider()
    }

    suspend fun getSubServiceItemsResponse(
        page: Int, serviceId: String
    ): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse> {
        return api.getSubServiceItemsResponse(page, serviceId)
    }


    suspend fun getProfile(): NetworkResponse<DevResponse<ProfileResponse>, ErrorResponse> {
        return api.getProfile(
            //    "Bearer" +PrefsHelper.getToken()
        )
    }

    suspend fun updateProfile(param: EditProfileParams): NetworkResponse<DevResponse<UserResponse>, ErrorResponse> {
        return api.updateProfile(
            param.toMap(), param.photo?.let { param.photo.toMultiPart("photo") },
        )
    }

    suspend fun changePassword(param: ChangePasswordParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.changePassword(param.oldPassword, param.newPassword)
    }

    suspend fun deleteAccount(): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.deleteAccount()
    }

    suspend fun complain(params: ComplainParams): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.complain(params.user_id, params.title, params.content)
    }

    suspend fun contactUs(params: ContactUsParams): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.contactUs(params.app_type, params.content,params.phone, params.countryCode)
    }

    suspend fun getGoal(): NetworkResponse<DevResponse<GoalResponse>, ErrorResponse> {
        return api.getGoal()
    }
    suspend fun getNotifications()
     =api.getNotifications()

    suspend fun getTermsProvider(): NetworkResponse<DevResponse<GoalResponse>, ErrorResponse> {
        return api.getTermsProvider()
    }

    suspend fun getProviders(params: GetProvidersParam): NetworkResponse<DevResponse<ProvidersResponse>, ErrorResponse> {
        return api.getProviders(params.service_id, params.lat, params.lon)
    }

    suspend fun getReviews(params: GetProvidersReviewsParam): NetworkResponse<DevResponse<ReviewsResponse>, ErrorResponse> {
        return api.getReviews(params.provider_id)
    }

    suspend fun getTax(): NetworkResponse<DevResponse<TaxResponse>, ErrorResponse> {
        return api.getTax()
    }

    suspend fun getOrders(params: GetOrderParam): NetworkResponse<DevResponse<OrderdResponse>, ErrorResponse> {
        return api.getOrders(params.order_status)
    }

    suspend fun cancelOrder(params: CancelOrderParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.cancelOrder(params.order_id, params.order_status)
    }

    suspend fun complainOrder(params: ComplainOrderParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.complainOrder(params.order_id, params.complaint)
    }

    suspend fun addReview(params: AddReviewsParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.addReview(
            params.provider_id, params.user_id, params.order_id, params.rate, params.comment
        )
    }

    @JvmName("createOrder1")
    suspend fun createOrder(params: CreateOrderParams): NetworkResponse<DevResponse<OrdersItem>, ErrorResponse> {
        return api.createOrder(params)
    }
     suspend fun getNationalities( ): NetworkResponse<DevResponse<NationalitiesResponse>, ErrorResponse> {
        return api.getNationalities( )
    }

    suspend fun updateFcm(params: FcmParam): NetworkResponse<DevResponse<Any>, ErrorResponse> {
        return api.updateFcm(params.token,0)
    }

    suspend fun getOrderById(params: OrderDetailsParam): NetworkResponse<DevResponse<OrdersItem>, ErrorResponse> {
        return api.getOrderById(params.id)
    }
    suspend fun payOrderWithVisa(params: PayOrderParam)= api.payOrderWithVisa(params.id, params.trans_ref, params.pay_token)
    suspend fun updateBalance(params: UpdateBalanceParam)= api.updateBalance(params.amount, params.trans_ref, params.pay_token)



}
