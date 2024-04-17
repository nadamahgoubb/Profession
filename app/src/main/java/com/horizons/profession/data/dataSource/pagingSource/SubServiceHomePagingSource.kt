package com.horizons.profession.data.dataSource.pagingSource

import com.horizons.profession.base.*
import com.horizons.profession.data.dataSource.Param.SubServicesParams
import com.horizons.profession.data.dataSource.repositoy.Repository
import com.horizons.profession.data.dataSource.response.SubServiceItemsResponse


class SubServiceHomePagingSource(private val repo: Repository,
                                 private val params: SubServicesParams
) : BasePagingDataSource<SubServiceItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<SubServiceItemsResponse>, ErrorResponse> {
        var data =repo.getSubServiceItemsResponse(params.page , params.service_id)
        return data
    }

}