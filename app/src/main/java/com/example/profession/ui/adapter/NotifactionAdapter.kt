package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
 import androidx.recyclerview.widget.RecyclerView
 import com.example.profession.data.dataSource.response.NotificationsItem
 import com.example.profession.databinding.ItemNotifactionBinding


class NotifactionAdapter : RecyclerView.Adapter<NotifactionAdapter.NotifactionViewHolder>() {
     var _binding: ItemNotifactionBinding? = null
    var list = mutableListOf<NotificationsItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: NotifactionViewHolder, position: Int) {
        var currentItem = list.get(position)
        holder.binding.tvNotiTxt.text = currentItem.body
        holder.binding.tvTime.text = currentItem.created_at?.split(" ")?.get(0)?.toString()



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifactionViewHolder {

        return NotifactionViewHolder(
            ItemNotifactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }



    class NotifactionViewHolder(var binding: ItemNotifactionBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int = list.size
}


