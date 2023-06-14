package com.example.profession.ui.fragments.checkout

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentCheckOutBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.Param.PaymentModel
import com.example.profession.data.dataSource.Param.ProvidersCreateOrderParams
import com.example.profession.ui.adapter.CheckoutPaymentAdapter
import com.example.profession.ui.adapter.CheckoutSubserviceAdapter
import com.example.profession.ui.adapter.PaymentCheckoutListener
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.Constants
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.loadImage
import com.example.profession.util.observe


class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(), PaymentCheckoutListener {
    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter_subservice: CheckoutSubserviceAdapter
    lateinit var adapter_payment: CheckoutPaymentAdapter

    var list_payment = arrayListOf<PaymentModel>()

    override fun onFragmentReady() {
        setupUi()
        onClick()
        loadData()
        mViewModel.apply {
            observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
    }
    var selectedServiceIds =  arrayListOf<String>()
    var providers =  arrayListOf<ProvidersCreateOrderParams>()
    private fun onClick() {

        binding.btnDone.setOnClickListener {
            if (mViewModel.paymentType == -1) {
                showToast(context?.resources?.getString(R.string.choose_payment_method))
            } else {
                for (i in mViewModel.selectedSubservice) {
                    i.id?.let { selectedServiceIds.add(it) }
                }
                for (i in mViewModel.selectedProviders) {
                    providers.add(
                        ProvidersCreateOrderParams(
                            i.id,
                            i.serviceTax,
                            i.serviceCostBeforeTax.toString(),
                            i.serviceTotalCost.toString()
                        )
                    )
                }
                mViewModel.submitOrder(selectedServiceIds, providers, binding.etMsg.text.toString())

            }
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun loadData() {
        list_payment.add(
            PaymentModel(
                Constants.CASH,
                resources.getString(R.string.cash),
                R.drawable.money
            )
        )
        list_payment.add(
            PaymentModel(
                Constants.VISA,
                resources.getString(R.string.visa),
                R.drawable.ic_visa
            )
        )
        list_payment.add(
            PaymentModel(
                Constants.WALLET,
                resources.getString(R.string.wallet),
                R.drawable.ic_wallet
            )
        )

        binding.tvTime.setText(mViewModel.hourToVisit + ":" + mViewModel.mintueToVisit + mViewModel.am)
        binding.tvDate.setText(mViewModel.current)
        binding.tvTimeinService.setText(mViewModel.hoursCount.toString() + resources.getText(R.string.hour))
        adapter_payment.itemsList = list_payment
        adapter_payment.notifyDataSetChanged()

        adapter_subservice.itemsList = mViewModel.selectedSubservice
        adapter_subservice.notifyDataSetChanged()


        // binding.tvName.setText(mViewModel.pr)

    }

    private fun handleViewState(action: CreateOrdersAction) {
        when (action) {
            is CreateOrdersAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }


            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }
            is CreateOrdersAction.ShowOrderSucess -> {
                showProgress(false)
                var bundle = Bundle()
                bundle.putString(Constants.ORDERID, action.data.id)
                findNavController().navigate(
                    R.id.orderSucessFragment,
                    bundle,
                    NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
                )
            }

            else -> {

            }
        }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        adapter_payment = CheckoutPaymentAdapter(this)
        binding.rvPaymentMethods.init(context, adapter_payment, 2)

        var servicetitle = mViewModel.selectedProviders.get(0).service?.name
        servicetitle?.let {
            adapter_subservice = CheckoutSubserviceAdapter(it)
            binding.rvSubservice.init(context, adapter_subservice, 2)
        }
        binding.item1.tvTitle.setText(getString(R.string.spare_parts))
        binding.item1.tvDesc.setText(getString(R.string.worker_price_include))
        binding.item1.iv.loadImage(R.drawable.ic_voiln)
        binding.item2.iv.loadImage(R.drawable.ic_settings_selected)
        binding.item2.tvTitle.setText(getString(R.string.unusal_workes))
        binding.item2.tvDesc.setText(getString(R.string.unusal_workes_msg))
    }

    override fun onPaymentClicked(item: PaymentModel?) {
        mViewModel.paymentType = item?.id
    }
}