package com.example.profession.data.dataSource.pagingSource

import com.example.profession.base.*
import com.example.profession.data.dataSource.Param.SubServicesParams
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.SubServiceItemsResponse


class SubServiceHomePagingSource(private val repo: Repository,
                                 private val params: SubServicesParams
) : BasePagingDataSource<SubServiceItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse> {
        var data =repo.getSubServiceItemsResponse(params.page , params.service_id)
        return data
    }

}