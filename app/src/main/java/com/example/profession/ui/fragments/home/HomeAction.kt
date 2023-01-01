package com.example.profession.ui.fragments.home

import androidx.paging.PagingData
import com.example.profession.base.Action
import com.example.profession.data.dataSource.response.ServicesItemsResponse
import com.example.profession.data.dataSource.response.SliderItemsResponse
import com.example.profession.data.dataSource.response.SubServiceItemsResponse


sealed class HomeAction() : Action {

    data class ShowLoading(val show: Boolean) : HomeAction()
    data class ShowFailureMsg(val message: String?) : HomeAction()

    data  class ShowService(val data: PagingData<ServicesItemsResponse>) : HomeAction()
    data  class ShowSubService(val data: PagingData<SubServiceItemsResponse>) : HomeAction()
    data  class ShowSlider(val data: PagingData<SliderItemsResponse>) : HomeAction()


}
