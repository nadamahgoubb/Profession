package com.example.profession.ui.adapter

import android.app.Service
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.dataSource.response.ServicesItemsResponse
 import com.example.profession.databinding.ItemHomeServiceBinding
import com.example.profession.ui.listener.ServiceOnClickListener
import com.example.profession.util.Constants
import com.example.profession.util.ext.loadImage


class ServicesHomeAdapter(
    var listener : ServiceOnClickListener,
    var   context: Context
//) : RecyclerView.Adapter<ServicesHomeAdapter.ServiceHomeViewHolder>() {
): PagingDataAdapter<ServicesItemsResponse, ServicesHomeAdapter.ServiceHomeViewHolder>(
    Service_DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ServiceHomeViewHolder, position: Int) {
 holder.binding.tvServiceName.setText(getItem(position)?.name)
   //    holder.binding.ivService.setImageDrawable(context.getDrawable(R.drawable.gr2))
      holder.binding.ivSerivce.loadImage(Constants.BaseUrl_Images+getItem(position)?.icon)
        holder.binding.root.setOnClickListener {
            listener.onServiceClickListener(getItem(position))
            //currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHomeViewHolder {

        return ServiceHomeViewHolder(
            ItemHomeServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class ServiceHomeViewHolder(val binding: ItemHomeServiceBinding) :
        RecyclerView.ViewHolder(binding.root)
    

    companion object {
        private val Service_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServicesItemsResponse>() {
            override fun areItemsTheSame(oldItem: ServicesItemsResponse, newItem: ServicesItemsResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ServicesItemsResponse, newItem: ServicesItemsResponse): Boolean =
                oldItem == newItem
        }
    }
}/*

     var _binding: ItemHomeServiceBinding? = null

    var itemsList = mutableListOf<ServiceResponse>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceHomeViewHolder {
        _binding = ItemHomeServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceHomeViewHolder(_binding!!)
    }


    override fun onBindViewHolder(holder: ServiceHomeViewHolder, position: Int) {
     //   var currentItem = itemsList[position]
      if(position ==1 ||position ==4 ||position ==5)
        holder.binding.ivService.setImageDrawable(context.getDrawable(R.drawable.gr1))
        else
          holder.binding.ivService.setImageDrawable(context.getDrawable(R.drawable.gr2))

        holder.binding.ivService.setOnClickListener {
         listener.onServiceClickListener()
             //currentItem)
        }
    }


    fun deleteItem(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemsList.size)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 6


}


*/