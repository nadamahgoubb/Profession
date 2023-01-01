package com.example.profession.data.dataSource.pagingSource

import com.example.profession.base.*
import com.example.profession.data.dataSource.Param.CityParams
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse


class CitiesPagingSource(private val repo: Repository,
                         private val params: CityParams
) : BasePagingDataSource<CitesItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse> {
        var data =repo.getCities(params.page,params.countryId)
        return data
    }

}