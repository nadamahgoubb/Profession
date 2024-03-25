package com.example.profession.ui.fragments.orderInfo

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
 import com.example.profession.R
import com.example.profession.databinding.FragmentOrderInfoBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.Param.CancelOrderParam
import com.example.profession.data.dataSource.Param.ComplainOrderParam
import com.example.profession.data.dataSource.Param.PayOrderParam
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
import com.example.profession.util.ext.loadImage
import com.example.profession.util.ext.roundTo
import com.example.profession.util.ext.toTwelevePattern
import com.example.profession.util.observe
import com.payment.paymentsdk.PaymentSdkActivity
import com.payment.paymentsdk.PaymentSdkConfigBuilder
import com.payment.paymentsdk.integrationmodels.PaymentSdkApms
import com.payment.paymentsdk.integrationmodels.PaymentSdkBillingDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkLanguageCode
import com.payment.paymentsdk.integrationmodels.PaymentSdkShippingDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkTokenise
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionClass
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionType
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderInfoFragment : BaseFragment<FragmentOrderInfoBinding>(), CallbackPaymentInterface {
    private lateinit var parent: MainActivity
    private val mViewModel: OrdersViewModel by activityViewModels()
    lateinit var adapter: OrdersAdapter

    lateinit var adapter_subservice: CheckoutSubserviceAdapter
    var status = "-1"
    override fun onFragmentReady() {

        setupUi()
        onClick()
        mViewModel.apply {
     //       getOrderDetails(mViewModel.orderId)
            observe(viewState) {
                handleViewState(it)
            }
        }

        binding.swiperefreshHome.setOnRefreshListener {
            mViewModel.getOrderDetails(mViewModel.orderId)
            if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
        }

    }
fun handleState(status:String){

        when (status) {
            Constants.New_ORDER -> {
                binding.cardWaitingApproval.isVisible = true
                binding.cardBill.isVisible = false
                binding.cardPersonalInfo.isVisible = false
                //     binding.cardCancelOrder.isVisible = true
            }
            Constants.CURRENT_ORDER -> {
                binding.cardWaitingApproval.isVisible = false
                binding.cardInProgress.isVisible = true
                binding.cardBill.isVisible = true
                binding.cardPersonalInfo.isVisible = true
            }
            Constants.PREV_ORDER -> {
                binding.cardInProgress.isVisible = false
                      binding.cardAddReview.isVisible = true
                  //    binding.cap.isVisible = true
                binding.cardCompelted.isVisible = true
                binding.cardCancelOrder.isVisible = false
                binding.cardBill.isVisible = true
                binding.cardPersonalInfo.isVisible = true
                //      binding.tvComplainAboutService.isVisible = true
            }
        }

}
    override fun onResume() {
        super.onResume()
        mViewModel.            getOrderDetails(mViewModel.orderId)

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
                     showProgress(false)
                    binding.cardPrice.isVisible = false
                }
            }
    is OrdersAction.ShowOrderPaid ->{
        action.message.let {
            showProgress(false)
            mViewModel.orderId.let { it1 -> mViewModel.getOrderDetails(it1) }
        }
            }

            is OrdersAction.ShowCanceledOrder ->{
                     showProgress(false)
                binding.cardPrice.isVisible = false
                activity?.onBackPressed()
            }

            is OrdersAction.ShowComplainedOrder ->{

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
        binding.tvOrderId.text = data.orderId
        this.orderId = data.orderId
        data.orderStatus?.let {status=it
            handleState(it) }
        data.paymentMethod?.let {
            var paymentData = getPaymentMethod(it, requireContext())
            binding.tvCash.text = paymentData?.title
            binding.ivLogoPayment.setImageDrawable(resources.getDrawable(paymentData?.logo!!))
            if(it==Constants.VISA){
                if(data.confirm_payment_visa==0&&data.finalTotal!=null) {
                    binding.cardPayOrder.isVisible = true
                    binding.cardCancelOrder.isVisible = false
                }
                else {
                    if(status==Constants.New_ORDER) {

                        binding.cardPrice.isVisible=true
                        binding.cardCancelOrder.isVisible = true
                        binding.cardPayOrder.isVisible = false
                    }
                    else {
                        binding.cardCancelOrder.isVisible = false
                        binding.cardPayOrder.isVisible = false
                        binding.cardPrice.isVisible=false
                    }

                }
            }else{
                if(status==Constants.New_ORDER||status==Constants.CURRENT_ORDER) binding.cardCancelOrder.isVisible = true
                else {
                    binding.cardPayOrder.isVisible = false
                    binding.cardCancelOrder.isVisible = false
                }

            }
        }

        binding.tvName.text = data.providerName
        binding.ivUser.loadImage(data.providerPhoto, placeHolderImage = R.drawable.empty_user, error_img =  R.drawable.empty_user)
        binding.tvRate.text = data.providerTotalRate.toString()
        binding.tvDesc.text = data.providerPreviousExperience
        binding.ivCall.setOnClickListener {
            data.providerPhone?.let { it1 -> call(it1) }
        }
        binding.tvTime.text = toTwelevePattern(data.orderTime)
        binding.tvDate.text = data.orderDate
        binding.tvPrice.text = data.providerHourPrice.toString()+ resources.getText(R.string.sr)
        binding.tvTimeinService.text = data.countHours.toString() + resources.getText(R.string.hour)
        binding.tvTotalBeforetax.text = data.total?.toDouble()?.roundTo(2)?.toString()+ " "+resources.getString(R.string.sr)
        binding.tvTotalPriceBottomSheet.text = data.total?.toDouble()?.roundTo(2)?.toString()+ " "+resources.getString(R.string.sr)
        binding.tvTax.text = data.tax?.roundTo(3).toString()
        binding.tvTotalPrice.text = data.finalTotal?.toDouble()?.roundTo(2)?.toString()+ " "+resources.getString(R.string.sr)

        adapter_subservice.itemsList = data.subServices
        adapter_subservice.notifyDataSetChanged()
if (status== Constants.PREV_ORDER){
    binding.cardPayOrder.isVisible = false
    if(data.user_complaint==0) {
     binding.tvComplainAboutService.isVisible = true
 }
 if(data.user_evaluation==0)   binding.cardAddReview.isVisible = true
 else{
     binding.cardPrice.isVisible= false
 }
}

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
                data.providerId, PrefsHelper.getUserData()?.id, rate, binding.etMsg.text.toString()
            )
         }
        binding.btnPayOrder.setOnClickListener {
            val configData = generatePaytabsConfigurationDetails()
            configData?.let { it1 ->
                PaymentSdkActivity.startCardPayment(
                    requireActivity(),
                    it1,
                    this
                )
            }
         }
        binding.rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rate = rating
             }
    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        var servicetitle = ""
        servicetitle.let {
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
    override fun onError(error: PaymentSdkError) {
        Log.i("TAG onError", error.msg.toString())
    }

    override fun onPaymentCancel() {
        Log.i("TAG", "onPaymentCancel")
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {

        Log.i("TAG", "Did payment success?: ${paymentSdkTransactionDetails.isSuccess}")
        var token = paymentSdkTransactionDetails.token
        var transRef = paymentSdkTransactionDetails.transactionReference
      /*  Toast.makeText(
            requireContext(),
            paymentSdkTransactionDetails.paymentResult?.responseMessage ?: "PaymentFinish",
            Toast.LENGTH_SHORT
        ).show()*/
        binding.cardPrice.isVisible=false
       transRef?.let {  mViewModel.PayOrder(PayOrderParam(mViewModel.orderId, token.toString(), it)) }
    }
    private fun generatePaytabsConfigurationDetails(selectedApm: PaymentSdkApms? = null): PaymentSdkConfigurationDetails? {
        val clientKey = "CGKMDK-VMKR6H-PT7NRM-GMPB7G"
        val serverKey = "SDJNLWHD6L-JH9GTMR2KT-ZDHLDDW9DN"
        val profileId  = "44353"
        val locale = PaymentSdkLanguageCode.EN /*Or PaymentSdkLanguageCode.AR*/
        val transactionTitle = "Test Pay"
        val orderId = mViewModel.orderId
        val cartDesc = "Cart description"
        val currency = "EGP"
        val amount = data.finalTotal
        val merchantCountryCode = "SA"
        val billingData = PaymentSdkBillingDetails(
            "City",
            "SA",
            "email1@domain.com",
            "name name",
            "+966568595106", "121321",
            "address street", ""
        )
        val shippingData = PaymentSdkShippingDetails(
            "City",
            "SA",
            "test@test.com",
            "name1 last1",
            "+966568595106", "3510",
            "street2", ""
        )
       amount?.toDoubleOrNull()?.let {
           val configData =      PaymentSdkConfigBuilder(
                profileId,
                serverKey,
                clientKey, it, currency
            )
                .setCartDescription(cartDesc)
                .setLanguageCode(locale)
                .setBillingData(billingData)
                .setMerchantCountryCode(merchantCountryCode)
                .setTransactionType(PaymentSdkTransactionType.SALE)
                .setTransactionClass(PaymentSdkTransactionClass.ECOM)
                //   .setShippingData(shippingData)
                .setTokenise(PaymentSdkTokenise.NONE) //Check other tokenizing types in PaymentSdkTokenise
                .setCartId(orderId)
                .showBillingInfo(false)
                .showShippingInfo(false)
                .forceShippingInfo(false)
                .setScreenTitle(transactionTitle)


        if (selectedApm != null)
            configData.setAlternativePaymentMethods(listOf(selectedApm))
        /*Check PaymentSdkApms for more payment options*/
           return configData.build()
       }
        return null
    }

}