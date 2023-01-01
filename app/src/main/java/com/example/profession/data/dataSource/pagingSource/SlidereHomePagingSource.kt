package com.example.profession.data.dataSource.pagingSource

import com.example.profession.base.*
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.SliderItemsResponse


class SliderHomePagingSource(private val repo: Repository,
                              private val params: PagingParams
) : BasePagingDataSource<SliderItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<SliderItemsResponse>, ErrorResponse> {
        var data =repo.getSlider(params.page)
        return data
    }

}