package com.example.profession.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profession.R
import com.example.profession.data.dataSource.Param.PaymentModel
import com.example.profession.databinding.ItemCheckoutPaymentMethodBinding


interface PaymentCheckoutListener {
    fun onPaymentClicked(item: PaymentModel?)
}

class CheckoutPaymentAdapter(
    var listener: PaymentCheckoutListener
) : RecyclerView.Adapter<CheckoutPaymentAdapter.CheckoutPaymentViewHolder>() {

    private var lastDefaultPosition = -1
    var _binding: ItemCheckoutPaymentMethodBinding? = null
lateinit var context :Context
    var itemsList = mutableListOf<PaymentModel>()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CheckoutPaymentViewHolder {
        _binding = ItemCheckoutPaymentMethodBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        context= parent.context
        return CheckoutPaymentViewHolder(_binding!!)
    }


    override fun onBindViewHolder(holder: CheckoutPaymentViewHolder, position: Int) {
        var currentItem = itemsList[position]

        holder.binding.tvTitle.text = currentItem.title
        holder.binding.ivLogo.setImageDrawable(currentItem.logo?.let {
            context.resources.getDrawable(it)
        })
        holder.binding.checkbox.isChecked = currentItem.selected == true

        holder.binding.checkbox.setOnClickListener {
            currentItem.id?.let {

                if (holder.binding.checkbox.isChecked) {
                    currentItem.selected = true
                    selectOneItemOnly(currentItem, position)

                } else {
                    currentItem.selected = false
                }
                listener.onPaymentClicked(currentItem)
            }
        }
        holder.binding.root.setOnClickListener {
            currentItem.id?.let {

                if (holder.binding.checkbox.isChecked) {
                    holder.binding.checkbox.isChecked = false
                    currentItem.selected = false
                } else {
                    holder.binding.checkbox.isChecked = true
                    currentItem.selected = true
                    selectOneItemOnly(currentItem, position)
                }
                listener.onPaymentClicked(currentItem)
            }
        }

    }
    fun selectOneItemOnly(item: PaymentModel, position: Int) {
        if (lastDefaultPosition != -1) {
            var lastDeafult = itemsList[lastDefaultPosition]

            updateItem(
                PaymentModel(
                    lastDeafult.id,
                    lastDeafult.title,
                    lastDeafult.logo,
                    false   )
            )
        }
        updateItem(item)
        lastDefaultPosition = position

    }

    fun updateItem(item: PaymentModel) {
        itemsList.indexOfFirst { item.id == it.id }.takeIf { it > -1 }?.let { pos ->
            itemsList[pos] = item
            notifyItemChanged(pos)
        }
    }
    override fun getItemCount(): Int =itemsList.size

    class CheckoutPaymentViewHolder(val binding: ItemCheckoutPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root)


}


