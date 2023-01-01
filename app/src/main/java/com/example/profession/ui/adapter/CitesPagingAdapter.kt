package com.example.profession.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.databinding.ItemFilterMultiChoiceBinding


interface CitesListener {

    fun onOrderClicked(item: CitesItemsResponse? )


}

class CitesPagingAdapter(
     var context:Context,
     var listener:CitesListener
    ) :
    PagingDataAdapter<CitesItemsResponse, CitesPagingAdapter.MyViewHolder>(Cities_DIFF_CALLBACK) {
    var lastPosition = -1

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
var currentItem= getItem(position)
        holder.binding.tvName.text = currentItem?.name

        holder.binding.checkbox.setOnClickListener {
            if (holder.binding.checkbox.isChecked == true) {
                currentItem?.choosen=true
                currentItem?.let { it1 -> selectOneItemOnly(it1, position) }
                currentItem?.let { it1 -> listener.onOrderClicked(it1) }
            } else {
                currentItem?.choosen=false
                listener.onOrderClicked(null)

            }   }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemFilterMultiChoiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }


    fun updateItem(item: CitesItemsResponse,pos:Int) {
    //    ca.indexOfFirst { item.id == it.id }
     //       .takeIf { it > -1 }?.let { pos ->
     //           list[pos] = item
getItem(pos)?.name=item.name
        getItem(pos)?.id=item.id
     //   getItem(pos)?.countryId=item.countryId
        getItem(pos)?.choosen=item.choosen

                notifyItemChanged(pos)
            }


    fun selectOneItemOnly(item: CitesItemsResponse, position: Int) {
        if (lastPosition != -1) {
            var lastChoosen = getItem(lastPosition)

            updateItem(
                item = CitesItemsResponse(
                    lastChoosen?.id,
                    lastChoosen?.name,
               //     lastChoosen?.countryId,
                   false ),position
            )
        }
        updateItem(item,position)
        lastPosition = position



    }
    class MyViewHolder(var binding: ItemFilterMultiChoiceBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    companion object {
        private val Cities_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CitesItemsResponse>() {
            override fun areItemsTheSame(oldItem: CitesItemsResponse, newItem: CitesItemsResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CitesItemsResponse, newItem: CitesItemsResponse): Boolean =
                oldItem == newItem
        }
    }
}


