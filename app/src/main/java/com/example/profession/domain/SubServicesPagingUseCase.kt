package com.example.profession.domain


import androidx.paging.PagingSource
import com.example.profession.base.BasePagingUseCase
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.Param.SubServicesParams
  import com.example.profession.data.dataSource.pagingSource.SubServiceHomePagingSource
import com.example.profession.data.dataSource.repositoy.Repository
 import com.example.profession.data.dataSource.response.SubServiceItemsResponse

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SubServicesPagingUseCase @Inject constructor(private val repo: Repository) :
    BasePagingUseCase<SubServiceItemsResponse, PagingParams>() {

    override fun getPagingSource(params: PagingParams?): PagingSource<Int, SubServiceItemsResponse> {
       var res= SubServiceHomePagingSource(repo, params!! as SubServicesParams)
        return res


    }
}
