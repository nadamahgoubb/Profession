package com.example.profession.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.data.dataSource.response.SubServiceItemsResponse
import com.example.profession.databinding.ItemSubServiceBinding
import com.example.profession.ui.listener.SubServiceListener


class SubServiceAdapter(
    var listener : SubServiceListener,
    var context:Context
    ) :
    PagingDataAdapter<SubServiceItemsResponse, SubServiceAdapter.MyViewHolder>(ORDER_DIFF_CALLBACK) {
 
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.tvTitle.text = getItem(position)?.name

        holder.binding.root.setOnClickListener {
            getItem(position)?.let { it1 -> listener.onSubServiceClickListener(it1) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemSubServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    class MyViewHolder(var binding: ItemSubServiceBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    companion object {
        private val ORDER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<SubServiceItemsResponse>() {
            override fun areItemsTheSame(oldItem: SubServiceItemsResponse, newItem: SubServiceItemsResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SubServiceItemsResponse, newItem: SubServiceItemsResponse): Boolean =
                oldItem == newItem
        }
    }
}


