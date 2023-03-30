package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.OrdersItem
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.data.dataSource.response.ProvidersResponse
import com.example.profession.databinding.ItemFilterMultiChoiceBinding
import com.example.profession.databinding.ItemOrdersBinding
import com.example.profession.databinding.ItemProviderBinding
import com.example.profession.util.Constants


interface ProviderClickListener {

    fun onProviderDetailsClicked(item: Providers?)
    fun onProviderAddedClicked(items: ArrayList<Providers>)

}

class ProvidersAdapter(
    var listener: ProviderClickListener
) : RecyclerView.Adapter<ProvidersAdapter.ProvidersViewHolder>() {
    var _binding: ItemProviderBinding? = null
    var list = mutableListOf<Providers>()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var context: Context
    var selectedProviders: ArrayList<Providers> = arrayListOf()
    override fun onBindViewHolder(holder: ProvidersViewHolder, position: Int) {
        var currentItem = list.get(position)
        holder.binding.tvName.text = currentItem?.name
        holder.binding.tvDistance.text = currentItem?.distance.toString()

        holder.binding.tvPrice.text = currentItem.hourPrice.toString()+context.resources.getString(R.string.sr)
        holder.binding.tvDesc.text = currentItem.previousExperience.toString()
        if (currentItem.choosen) holder.binding.checkbox.isChecked = true
        else holder.binding.checkbox.isChecked = false

        holder.binding.root.setOnClickListener {
            listener.onProviderDetailsClicked(currentItem)
        }
        holder.binding.checkbox.setOnClickListener {
            if (holder.binding.checkbox.isChecked) {
                currentItem?.let { it1 -> selectedProviders.add(it1) }
                currentItem?.choosen = true
            } else {
                selectedProviders.remove(currentItem)
                currentItem?.choosen = false

            }
            currentItem?.let { it1 -> listener.onProviderAddedClicked(selectedProviders) }
        }
        holder.binding.lytCheck.setOnClickListener {
            if (holder.binding.checkbox.isChecked) {
                selectedProviders.remove(currentItem)
                currentItem?.choosen = false
                holder.binding.checkbox.isChecked = false

            } else {

                currentItem?.let { it1 -> selectedProviders.add(it1) }
                currentItem?.choosen = true
                holder.binding.checkbox.isChecked = true
            }
            currentItem?.let { it1 -> listener.onProviderAddedClicked(selectedProviders) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvidersViewHolder {

        _binding = ItemProviderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
        return ProvidersViewHolder(_binding!!)
    }


    class ProvidersViewHolder(var binding: ItemProviderBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    override fun getItemCount(): Int = list.size
}


