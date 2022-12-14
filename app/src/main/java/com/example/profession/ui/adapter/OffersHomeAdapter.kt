package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.ServiceResponse
import com.example.profession.databinding.ItemHomeOffersBinding
import com.example.profession.databinding.ItemHomeServiceBinding
import com.example.profession.ui.listener.ServiceOnClickListener


class OffersHomeAdapter(
    var listener : ServiceOnClickListener,
    var   context: Context
) : RecyclerView.Adapter<OffersHomeAdapter.InProgressCompeletedViewHolder>() {

     var _binding: ItemHomeOffersBinding? = null

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
        _binding = ItemHomeOffersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InProgressCompeletedViewHolder(_binding!!)
    }


    override fun onBindViewHolder(holder: InProgressCompeletedViewHolder, position: Int) {
      //  var currentItem = itemsList[position]
      if(position ==1 ||position ==4 ||position ==5)
        holder.binding.ivOffers.setImageDrawable(context.getDrawable(R.drawable.slider1))
        else
          holder.binding.ivOffers.setImageDrawable(context.getDrawable(R.drawable.slider2))

        holder.binding.ivOffers.setOnClickListener {
     //    listener.onServiceClickListener()
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

    class InProgressCompeletedViewHolder(val binding: ItemHomeOffersBinding) :
        RecyclerView.ViewHolder(binding.root)


}

