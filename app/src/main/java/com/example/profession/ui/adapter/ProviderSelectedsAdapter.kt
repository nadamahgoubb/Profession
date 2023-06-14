package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.databinding.ItemProviderTaxBinding
import com.example.profession.util.ext.loadImage
import com.example.profession.util.ext.roundTo


interface ProviderSelectedsClickListener {

    fun onProviderCancelClicked(item: Providers?, position: Int)

}

class ProviderSelectedAdapter(
  var listener: ProviderSelectedsClickListener
) : RecyclerView.Adapter<ProviderSelectedAdapter.ProviderSelectedViewHolder>() {
    var _binding: ItemProviderTaxBinding? = null
    var list = mutableListOf<Providers>()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var context :Context
     override fun onBindViewHolder(holder: ProviderSelectedViewHolder, position: Int) {
        var currentItem = list.get(position)
        holder.binding.tvName.text =  currentItem?.name

         holder.binding.ivUser.loadImage(currentItem.photo)

        holder.binding.tvRate.text=currentItem.totalRate.roundTo(2).toString()
        holder.binding.tvCost.text=(currentItem.serviceCostBeforeTax.toString() +context.resources.getString(R.string.sr))
        holder.binding.tvTax.text=(currentItem.serviceTax.toString()+context.resources.getString(R.string.sr))
        holder.binding.tvTotal.text=(currentItem.serviceTotalCost.toString()+context.resources.getString(R.string.sr))


        holder.binding.ivCancel.setOnClickListener {
            listener.onProviderCancelClicked(currentItem, position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderSelectedViewHolder {

        _binding = ItemProviderTaxBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context = parent.context
      return ProviderSelectedViewHolder(_binding!!)
    }
    fun deleteItem(position: Int) {

        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
        notifyDataSetChanged()
    }

    class ProviderSelectedViewHolder(var binding: ItemProviderTaxBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    override fun getItemCount(): Int = list.size
}


