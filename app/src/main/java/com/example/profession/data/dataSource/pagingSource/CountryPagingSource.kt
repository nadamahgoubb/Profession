package com.example.profession.data.dataSource.pagingSource

import com.example.profession.base.*
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse


class CountryPagingSource(private val repo: Repository,
                          private val params: PagingParams
) : BasePagingDataSource<CitesItemsResponse>() {


    override val queryParams: PagingParams = params

    override suspend fun execute(): NetworkResponse<BasePagingResponse<CitesItemsResponse>, ErrorResponse> {
        var data =repo.getCountries(params.page)
        return data
    }

}