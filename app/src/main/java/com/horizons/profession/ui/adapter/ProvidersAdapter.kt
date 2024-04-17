package com.horizons.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.horizons.profession.R
import com.horizons.profession.data.dataSource.response.Providers
import com.horizons.profession.databinding.ItemProviderBinding
import com.horizons.profession.util.ext.loadImage


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
        holder.binding.tvName.text = currentItem.name
        holder.binding.tvDistance.text = currentItem.distance.toString() + " "+context.resources.getString(R.string.km)
holder.binding.ivUser.loadImage(currentItem.photo)
        holder.binding.tvPrice.text = currentItem.hourPrice.toString()+" "+context.resources.getString(R.string.sr)
        holder.binding.tvDesc.text = currentItem.previousExperience.toString()
        holder.binding.tvRate.text = currentItem.totalRate.toString()
        holder.binding.checkbox.isChecked = currentItem.choosen

        holder.binding.root.setOnClickListener {
            listener.onProviderDetailsClicked(currentItem)
        }
        holder.binding.checkbox.setOnClickListener {
            if (holder.binding.checkbox.isChecked) {
                currentItem.let { it1 -> selectedProviders.add(it1) }
                currentItem.choosen = true
            } else {
                selectedProviders.remove(currentItem)
                currentItem.choosen = false

            }
            currentItem.let { it1 -> listener.onProviderAddedClicked(selectedProviders) }
        }
        holder.binding.lytCheck.setOnClickListener {
            if (holder.binding.checkbox.isChecked) {
                selectedProviders.remove(currentItem)
                currentItem.choosen = false
                holder.binding.checkbox.isChecked = false

            } else {

                currentItem.let { it1 -> selectedProviders.add(it1) }
                currentItem.choosen = true
                holder.binding.checkbox.isChecked = true
            }
            currentItem.let { it1 -> listener.onProviderAddedClicked(selectedProviders) }
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
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int = list.size
}


