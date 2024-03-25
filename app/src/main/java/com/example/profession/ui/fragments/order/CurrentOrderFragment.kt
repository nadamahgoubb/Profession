package com.example.profession.ui.fragments.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.OrdersItem
import com.example.profession.databinding.FragmentOrderItemBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.adapter.OrdersAdapter
import com.example.profession.ui.adapter.OrdersClickListener
import com.example.profession.util.Constants
import com.example.profession.util.Extension
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CurrentOrderFragment : BaseFragment<FragmentOrderItemBinding>(), OrdersClickListener {
    private val mViewModel: OrdersViewModel by activityViewModels()
    lateinit var adapter: OrdersAdapter
    override fun onFragmentReady() {

        initAdapter()
        onClick()
        mViewModel.apply {
            getOrders(Constants.CURRENT_ORDER)
            observe(viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
            mViewModel.getOrders(Constants.CURRENT_ORDER)
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
        }
    }

    private fun onClick() {
        binding.btnGohome.setOnClickListener {
            showActivity(MainActivity::class.java, clearAllStack = true)
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
                if (action.state == Constants.CURRENT_ORDER) {
                    if (action.data.orders.isNullOrEmpty() == false) {
                        binding.lytEmptyState.isVisible = false
                        adapter.list = action.data.orders
                        adapter.notifyDataSetChanged()
                    } else {
                        binding.lytEmptyState.isVisible = true
                        adapter.list = arrayListOf()
                        adapter.notifyDataSetChanged()  }
                }
            }

            is OrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    var bundle = Bundle()

    private fun initAdapter() {
        adapter = OrdersAdapter(Constants.CURRENT_ORDER, this)
        binding.rvOrders.init(requireContext(), adapter, 2)
        adapter.list.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onOrderDetailsClicked(item: OrdersItem?) {
        bundle.putString(Constants.STATUS, Constants.CURRENT_ORDER)
        mViewModel.orderId = item?.orderId.toString()
        findNavController().navigate(R.id.orderInfoFragment, bundle)
    }

    override fun onOrderCallClicked(item: OrdersItem?) {
        item?.providerPhone?.let { call(it) }

    }

    override fun onOrderChatClicked(item: OrdersItem?) {
        item?.providerPhone?.let {
            item.countryCode?.let { it1 ->
                Extension.chat(

                    requireContext(), it1, it
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