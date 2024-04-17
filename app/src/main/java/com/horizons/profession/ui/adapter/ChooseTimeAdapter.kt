package com.horizons.profession.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.horizons.profession.data.TimeResponse
import com.horizons.profession.databinding.ItemTimeBinding
import com.horizons.profession.ui.listener.ChooseTimeOnClickListener


class ChooseTimeAdapter(
    var listener : ChooseTimeOnClickListener,
 ) : RecyclerView.Adapter<ChooseTimeAdapter.ChooseTimeViewHolder>() {

    var _binding: ItemTimeBinding? = null

    var itemsList = mutableListOf<TimeResponse>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChooseTimeViewHolder {
        _binding = ItemTimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChooseTimeViewHolder(_binding!!)
    }


    override fun onBindViewHolder(holder: ChooseTimeViewHolder, position: Int) {
        //   var currentItem = itemsList[position]
         //    holder.binding.checkbox.text =
 
        holder.binding.checkbox.setOnClickListener {
            listener.onChooseTimeClickListener()
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

    class ChooseTimeViewHolder(val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root)


}


