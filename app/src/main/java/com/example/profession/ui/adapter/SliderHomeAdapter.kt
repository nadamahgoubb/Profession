package com.example.profession.ui.adapter

 import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
  import com.example.profession.data.dataSource.response.SliderItemsResponse
import com.example.profession.databinding.ItemHomeOffersBinding
import com.example.profession.ui.listener.SliderListener
 import com.example.profession.util.Constants
 import com.example.profession.util.ext.loadImage


class SliderHomeAdapter(
    var listener : SliderListener,
    var   context: Context
    ): PagingDataAdapter<SliderItemsResponse, SliderHomeAdapter.SliderHomeViewHolder>(SLIDER_DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: SliderHomeViewHolder, position: Int) {

       holder.binding.ivOffers.setImageDrawable(context.getDrawable(R.drawable.slider1))
    //    holder.binding.ivOffers.loadImage(Constants.BaseUrl_Images+getItem(position)?.image)

        holder.binding.root.setOnClickListener {
            getItem(position)?.let { it1 -> listener.onSliderClickListener(it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHomeViewHolder {

        return SliderHomeViewHolder(
            ItemHomeOffersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class SliderHomeViewHolder(val binding: ItemHomeOffersBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val SLIDER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<SliderItemsResponse>() {
            override fun areItemsTheSame(oldItem: SliderItemsResponse, newItem: SliderItemsResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SliderItemsResponse, newItem: SliderItemsResponse): Boolean =
                oldItem == newItem
        }
    }
}



