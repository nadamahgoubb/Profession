package com.example.profession.domain


import androidx.paging.PagingSource
import com.example.profession.base.BasePagingUseCase
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.pagingSource.ServiceHomePagingSource
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.ServicesItemsResponse

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
