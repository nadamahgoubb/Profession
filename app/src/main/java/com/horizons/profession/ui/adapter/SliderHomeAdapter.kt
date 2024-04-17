package com.horizons.profession.ui.adapter

 import android.annotation.SuppressLint
 import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
 import androidx.recyclerview.widget.RecyclerView
 import com.horizons.profession.data.dataSource.response.SliderItemsResponse
import com.horizons.profession.databinding.ItemHomeOffersBinding
import com.horizons.profession.ui.listener.SliderListener
 import com.horizons.profession.util.ext.loadImage


class SliderHomeAdapter(
    var listener : SliderListener,
    var   context: Context
    ):   RecyclerView.Adapter<SliderHomeAdapter.SliderHomeViewHolder>() {

    var _binding: ItemHomeOffersBinding? = null
    var list = mutableListOf<SliderItemsResponse>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: SliderHomeViewHolder, position: Int) {
var current = list.get(position)
       holder.binding.ivOffers.loadImage(list[position].image)
     //       holder.binding.ivOffers.setImageDrawable(context.getDrawable(R.drawable.slider1))

        holder.binding.root.setOnClickListener {
            listener.onSliderClickListener(current) }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHomeViewHolder {

        _binding=
            ItemHomeOffersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return  SliderHomeViewHolder(_binding!!)
    }

    override fun getItemCount(): Int = list.size

    class SliderHomeViewHolder(val binding: ItemHomeOffersBinding) :
        RecyclerView.ViewHolder(binding.root)


}


