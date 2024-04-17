package com.horizons.profession.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
 import com.horizons.profession.data.dataSource.response.*
import com.horizons.profession.databinding.ItemReviewsBinding
import com.horizons.profession.util.ext.loadImage
import com.horizons.profession.util.ext.roundTo


class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {
    var _binding: ItemReviewsBinding? = null
    var list = mutableListOf<Reviews>()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        var currentItem = list.get(position)
        holder.binding.tvName.text = currentItem.userName
        holder.binding.tvRate.text = currentItem.rate?.roundTo(2).toString()
        holder.binding.tvDesc.text = currentItem.comment
        holder.binding.ivUser.loadImage(currentItem.providerPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {

        _binding = ItemReviewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ReviewsViewHolder(_binding!!)
    }


    class ReviewsViewHolder(var binding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int = list.size
}


