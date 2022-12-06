package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.ServiceResponse
import com.example.profession.databinding.ItemHomeServiceBinding
import com.example.profession.ui.listener.ServiceOnClickListener


class ServicesHomeAdapter(
    var listener : ServiceOnClickListener,
    var   context: Context
) : RecyclerView.Adapter<ServicesHomeAdapter.InProgressCompeletedViewHolder>() {

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
    ): InProgressCompeletedViewHolder {
        _binding = ItemHomeServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InProgressCompeletedViewHolder(_binding!!)
    }


    override fun onBindViewHolder(holder: InProgressCompeletedViewHolder, position: Int) {
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

    class InProgressCompeletedViewHolder(val binding: ItemHomeServiceBinding) :
        RecyclerView.ViewHolder(binding.root)


}


