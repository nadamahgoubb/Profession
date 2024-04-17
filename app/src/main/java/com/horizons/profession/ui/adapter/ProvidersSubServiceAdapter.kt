package com.horizons.profession.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.horizons.profession.data.dataSource.response.SubServiceItemsResponse
import com.horizons.profession.databinding.ItemProviderServiceDetailsBinding
import com.horizons.profession.util.ext.loadImage


class ProvidersSubServiceAdapter : RecyclerView.Adapter<ProvidersSubServiceAdapter.ProvidersSubServiceViewHolder>() {

    var _binding: ItemProviderServiceDetailsBinding? = null
    var list = mutableListOf<SubServiceItemsResponse>()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ProvidersSubServiceViewHolder, position: Int) {
        var currentItem = list.get(position)
        holder.binding.tvTitle.text = currentItem.name
        holder.binding.ivImg.loadImage(currentItem.icon)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProvidersSubServiceViewHolder {
        _binding = ItemProviderServiceDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ProvidersSubServiceViewHolder(_binding!!)
    }


    class ProvidersSubServiceViewHolder(var binding: ItemProviderServiceDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int = list.size
}


