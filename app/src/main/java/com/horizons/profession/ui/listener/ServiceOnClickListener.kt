package com.horizons.profession.ui.listener

 import com.horizons.profession.data.dataSource.response.ServicesItemsResponse
 import com.horizons.profession.data.dataSource.response.SliderItemsResponse
import com.horizons.profession.data.dataSource.response.SubServiceItemsResponse


interface ServiceOnClickListener {
    fun onServiceClickListener(item: ServicesItemsResponse?)
}

interface SubServiceListener {
    fun onSubServiceClickListener(items: ArrayList<SubServiceItemsResponse>)
}

interface SliderListener {
    fun onSliderClickListener(item: SliderItemsResponse)
}