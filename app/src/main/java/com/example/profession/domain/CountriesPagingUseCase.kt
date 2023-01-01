package com.example.profession.domain


import androidx.paging.PagingSource
import com.example.profession.base.BasePagingUseCase
import com.example.profession.base.PagingParams
import com.example.profession.data.dataSource.pagingSource.CountryPagingSource
import com.example.profession.data.dataSource.repositoy.Repository
import com.example.profession.data.dataSource.response.CitesItemsResponse

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CountriesPagingUseCase @Inject constructor(private val repo: Repository) :
    BasePagingUseCase<CitesItemsResponse, PagingParams>() {

    override fun getPagingSource(params: PagingParams?): PagingSource<Int, CitesItemsResponse> {
       var res= CountryPagingSource(repo, params!!)
        return res


    }
}
