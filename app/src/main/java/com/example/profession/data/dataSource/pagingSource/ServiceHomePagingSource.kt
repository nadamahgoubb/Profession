package com.example.profession.data.dataSource.pagingSource

import com.example.profession.base.*
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.ServicesItemsResponse


class ServiceHomePagingSource(private val repo: Repository,
                              private val params: PagingParams
) : BasePagingDataSource<ServicesItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<ServicesItemsResponse>, ErrorResponse> {
        var data =repo.getServices(params.page)
        return data
    }

}