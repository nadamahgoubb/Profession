package com.example.profession.ui.fragments.orderInfo


import android.content.Intent
import android.net.Uri
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.laundrydelivery.util.ext.roundTo
import com.example.profession.R
import com.example.profession.databinding.FragmentOrderInfoBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.Param.CancelOrderParam
import com.example.profession.data.dataSource.Param.ComplainOrderParam
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.OrdersItem
import com.example.profession.ui.adapter.CheckoutSubserviceAdapter
import com.example.profession.ui.adapter.OrdersAdapter
import com.example.profession.ui.dialog.ComplainSheetFragment
import com.example.profession.ui.dialog.OnClickComplain
import com.example.profession.ui.fragments.order.OrdersAction
import com.example.profession.ui.fragments.order.OrdersViewModel
import com.example.profession.util.Constants
import com.example.profession.util.Utils.getPaymentMethod
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderInfoFragment : BaseFragment<FragmentOrderInfoBinding>() {
    private lateinit var parent: MainActivity
    private val mViewModel: OrdersViewModel by activityViewModels()
    lateinit var adapter: OrdersAdapter

    lateinit var adapter_subservice: CheckoutSubserviceAdapter
    var status = "-1"
    override fun onFragmentReady() {

        setupUi()
        onClick()
        mViewModel.apply {
            getOrderDetails(mViewModel.orderId)
            observe(viewState) {
                handleViewState(it)
            }
        }
        (arguments?.getString("ORDERID"))?.let {
            status = it

            when (it) {
                Constants.New_ORDER -> {
                    binding.cardWaitingApproval.isVisible = true
                    binding.cardBill.isVisible = false
                    binding.cardPersonalInfo.isVisible = false
                    binding.cardCancelOrder.isVisible = true
                }
                Constants.CURRENT_ORDER -> {
                    binding.cardInProgress.isVisible = true
                    binding.cardPayOrder.isVisible = true
                    binding.cardBill.isVisible = true
                    binding.cardPersonalInfo.isVisible = true
                }
                Constants.PREV_ORDER -> {
                    binding.cardAddReview.isVisible = true
                    binding.cardCompelted.isVisible = true
                    binding.cardBill.isVisible = true
                    binding.cardPersonalInfo.isVisible = true
                    binding.tvComplainAboutService.isVisible = true
                }


            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
            mViewModel.getOrderDetails(mViewModel.orderId)
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
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
            is OrdersAction.ShowOrderDetails -> {
                showProgress(false)
                showData(action.data)
            }

            is OrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }
            is OrdersAction.ShowReviewAdded ->{
                action.message?.let {
                    showToast(action.message)
                    showProgress(false)
                    binding.cardPrice.isVisible = false
                }
            }

            is OrdersAction.ShowCanceledOrder ->{
                     showToast(action.message)
                    showProgress(false)
                binding.cardPrice.isVisible = false
            }

            is OrdersAction.ShowComplainedOrder ->{
                showToast(action.message)
                binding.tvComplainAboutService.isVisible = false
                showProgress(false)
            }
            else -> {

            }
        }
    }

    lateinit var data: OrdersItem
    var orderId: String? = null
    private fun showData(data: OrdersItem) {
        binding.lytData.isVisible = true
        this.data = data
        binding.tvOrderId.setText(data.orderId)
        this.orderId = data.orderId
        data.paymentMethod?.let {
            var paymentData = getPaymentMethod(it, requireContext())
            binding.tvCash.setText(paymentData?.title)
            binding.ivLogoPayment.setImageDrawable(
                resources.getDrawable(paymentData?.logo!!)
            )
        }

        binding.tvName.setText(data?.providerName)
        binding.tvRate.setText(data?.providerTotalRate.toString())
        binding.tvDesc.setText(data?.providerPreviousExperience)
        binding.ivCall.setOnClickListener {
            data.providerPhone?.let { it1 -> call(it1) }
        }
        binding.tvTime.setText(data.orderTime)
        binding.tvDate.setText(data.orderDate)
        binding.tvPrice.setText(data.providerHourPrice.toString())
        binding.tvTimeinService.setText(data.countHours.toString() + resources.getText(R.string.hour))
        binding.tvTotalBeforetax.setText(data.total?.toString())
        binding.tvTax.setText(data.tax?.roundTo(3).toString())
        binding.tvTotalPrice.setText(data.finalTotal)

        adapter_subservice.itemsList = data.subServices
        adapter_subservice.notifyDataSetChanged()


    }

    fun call(tel: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + tel)
        startActivity(dialIntent)
    }

    var rate = 0F
    private fun onClick() {
        binding.tvComplainAboutService.setOnClickListener {
            showComplainBotttomSheetFragment()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnReject.setOnClickListener {
            orderId?.let { it1 -> mViewModel.cancelOrder(CancelOrderParam(it1, Constants.CANCEL)) }
        }
        binding.tvCancelOrder.setOnClickListener {
            orderId?.let { it1 -> mViewModel.cancelOrder(CancelOrderParam(it1, Constants.CANCEL)) }
        }
        binding.btnDone.setOnClickListener {
            mViewModel.validateAddReview(
                data?.providerId, PrefsHelper.getUserData()?.id, rate, binding.etMsg.text.toString()
            )
         }
        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rate = rating
                Toast.makeText(requireContext(), rating.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        var servicetitle = ""
        servicetitle?.let {
            adapter_subservice = CheckoutSubserviceAdapter(it)
            binding.rvSubservice.init(context, adapter_subservice, 2)
        }

    }

    private fun showComplainBotttomSheetFragment() {
        ComplainSheetFragment.newInstance(object : OnClickComplain {
            override fun onClick(comment: String) {
                orderId?.let { it1 -> mViewModel.complainOrder(ComplainOrderParam(it1, comment)) }
            }

        }).show(childFragmentManager, ComplainSheetFragment::class.java.canonicalName)
    }

}