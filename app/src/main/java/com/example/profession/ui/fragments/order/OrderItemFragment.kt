package com.example.profession.ui.fragments.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
 import androidx.fragment.app.viewModels
 import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.OrdersItem
import com.example.profession.databinding.FragmentOrderItemBinding
import com.example.profession.ui.adapter.OrdersAdapter
import com.example.profession.ui.adapter.OrdersClickListener
import com.example.profession.util.Constants
import com.example.profession.util.Extension.chat
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class OrderItemFragment(var state: String) : BaseFragment<FragmentOrderItemBinding>(),
    OrdersClickListener {
    private val mViewModel: OrdersViewModel by viewModels()
    lateinit var adapter: OrdersAdapter
    override fun onFragmentReady() {
        when (state) {
            Constants.New_ORDER -> {
                mViewModel.getOrders(Constants.New_ORDER)
            }
            Constants.CURRENT_ORDER -> {
                mViewModel.getOrders(Constants.CURRENT_ORDER)
            }
            Constants.PREV_ORDER -> {
                mViewModel.getOrders(Constants.PREV_ORDER)
            }
        }
        initAdapter()
        mViewModel.apply {
            getOrders(state)
            observe(viewState) {
                handleViewState(it)
            }
        }

    }

    private fun handleViewState(action: OrdersAction) {
        when (action) {
            is OrdersAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is OrdersAction.ShowOrders -> {
                showProgress(false)
                adapter.list = action.data.orders
                adapter.notifyDataSetChanged()
            }

            is OrdersAction.ShowFailureMsg ->
                action.message?.let {
                    showToast(action.message)
                    showProgress(false)

                }

            else -> {

            }
        }
    }

    var bundle = Bundle()

    private fun initAdapter() {
        adapter = OrdersAdapter(state, this)
        binding.rvOrders.init(requireContext(), adapter, 2)
    }

    override fun onOrderDetailsClicked(item: OrdersItem?) {
        bundle.putString(Constants.ORDERID, item?.orderId)
        findNavController().navigate(R.id.orderInfoFragment, bundle)
    }

    override fun onOrderCallClicked(item: OrdersItem?) {
        item?.providerPhone?.let { call(it) }

    }

    override fun onOrderChatClicked(item: OrdersItem?) {
        item?.providerPhone?.let {
            item.countryCode?.let { it1 ->
                chat(
                    requireContext(),
                    it1,
                    it
                )
            }
        }
    }

    fun call(tel: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + tel)
        startActivity(dialIntent)
    }
}