package com.example.profession.ui.listener

 import com.example.profession.data.dataSource.response.ServicesItemsResponse
 import com.example.profession.data.dataSource.response.SliderItemsResponse
import com.example.profession.data.dataSource.response.SubServiceItemsResponse


interface ServiceOnClickListener {
    fun onServiceClickListener(item: ServicesItemsResponse?)
}

interface SubServiceListener {
    fun onSubServiceClickListener(items: ArrayList<SubServiceItemsResponse>)
}

interface SliderListener {
    fun onSliderClickListener(item: SliderItemsResponse)
}
