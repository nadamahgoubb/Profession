package com.example.profession.domain


import androidx.paging.PagingSource
import com.example.profession.base.BasePagingUseCase
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.pagingSource.CountryPagingSource
import com.example.profession.data.dataSource.pagingSource.ServiceHomePagingSource
import com.example.profession.data.dataSource.pagingSource.SliderHomePagingSource
import com.example.profession.data.dataSource.pagingSource.SubServiceHomePagingSource
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.ServicesItemsResponse
import com.example.profession.data.dataSource.response.SliderItemsResponse
import com.example.profession.data.dataSource.response.SubServiceItemsResponse

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SliderPagingUseCase @Inject constructor(private val repo: Repository) :
    BasePagingUseCase<SliderItemsResponse, PagingParams>() {

    override fun getPagingSource(params: PagingParams?): PagingSource<Int, SliderItemsResponse> {
       var res= SliderHomePagingSource(repo, params!!)
        return res


    }
}
