package com.horizons.profession.domain


import androidx.paging.PagingSource
import com.horizons.profession.base.BasePagingUseCase
import com.horizons.profession.base.PagingParams
import com.horizons.profession.data.dataSource.pagingSource.ServiceHomePagingSource
import com.horizons.profession.data.dataSource.repositoy.Repository
import com.horizons.profession.data.dataSource.response.ServicesItemsResponse

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ServicesPagingUseCase @Inject constructor(private val repo: Repository) :
    BasePagingUseCase<ServicesItemsResponse, PagingParams>() {

    override fun getPagingSource(params: PagingParams?): PagingSource<Int, ServicesItemsResponse> {
       var res= ServiceHomePagingSource(repo, params!!)
        return res


    }
}
