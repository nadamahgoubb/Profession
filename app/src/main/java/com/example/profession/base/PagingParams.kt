package com.example.profession.ui.base


abstract class PagingParams : HashMapParams {
    abstract var page: Int
}

interface HashMapParams {
    fun dataClass(): Any
}
data class  OrderHistoryParams(var status:Int): PagingParams() {
    override fun dataClass(): Any = this
    override var page: Int = 0
        //INITIAL_PAGE_INDEX
}