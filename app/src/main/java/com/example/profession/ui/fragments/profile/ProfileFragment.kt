package com.example.profession.ui.fragments.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.Param.AddressParams
import com.example.profession.data.dataSource.Param.PayOrderParam
import com.example.profession.data.dataSource.Param.UpdateBalanceParam
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.ProfileResponse
import com.example.profession.ui.adapter.CitesListener
import com.example.profession.ui.dialog.*
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.map.MapBottomSheet
import com.example.profession.ui.fragments.map.onLocationClick
import com.example.profession.ui.fragments.profile.ProfileViewModel.Companion.getAllCountries
import com.example.profession.ui.fragments.profile.ProfileViewModel.Companion.getCurrentCountryName
import com.example.profession.util.*
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.isNull
import com.example.profession.util.ext.loadImage
import com.example.profession.util.ext.roundTo
import com.google.android.material.appbar.AppBarLayout
import com.hbb20.CountryCodePicker
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
import kotlinx.android.synthetic.main.nav_header.view.iv_user
import kotlinx.android.synthetic.main.nav_header.view.tv_name
import okhttp3.internal.cache2.Relay.Companion.edit
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(),
    CountryCodePicker.OnCountryChangeListener, CallbackPaymentInterface {
    private var countryCode: String = "+966"
    private lateinit var parent: MainActivity
    private val mViewModel: ProfileViewModel by viewModels()
    var cityID: String = ""
    var data: ProfileResponse? = null
    var countryId: String = ""

    var lat: Double? = null
    var long: Double? = null

    @Inject
    lateinit var permissionManager: PermissionManager
    var verified_countryCode = ""
    var verified_phone: String? = null

    @Inject
    lateinit var locationManager: WWLocationManager
    var state = 0 // show data  1->edit
    override fun onFragmentReady() {
        onClick()
        stateShowData()
        setupUi()
        mViewModel.apply {
            getProfile()
            getAllCountry(getCurrentCountryName)
            observe(viewState) {
                handleViewState(it)
            }
        }
    }

    private fun handleViewState(action: ProfileAction) {
        when (action) {
            is ProfileAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }

            is ProfileAction.ShowProfile -> {
                showProgress(false)
                showData(action.data)
            }

            is ProfileAction.ShowBalance -> {
                showProgress(false)
                mViewModel.getProfile()
            }

            is ProfileAction.ShowUpdatesProfile -> {
                showProgress(false)
                showToast(action.message)
                stateShowData()
               parent.reloadImage()

            }

            is ProfileAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            is ProfileAction.ShowAllCities -> {
                showProgress(false)

                action.data.cities?.let {
                    if (action.type == getAllCountries) openCitiesDialog(it)
                    else binding.etCity.text = searchInCiteies(cityID, it)?.name

                }
            }

            is ProfileAction.ShowAllCountry -> {

                showProgress(false)

                action.data.countries?.let {
                    if (action.type == getAllCountries) openCountriesDialog(it)
                    else binding.etCoutry.text = searchInCiteies(countryId, it)?.name
                }
            }

            is ProfileAction.DeleteAccount -> {
                showProgress(false)
                showToast(action.message)
                PrefsHelper.clear()
                var intent = Intent(activity, AuthActivity::class.java)
                intent.putExtra(Constants.Start, Constants.login)
                startActivity(intent)
                activity?.finish()
            }

            is ProfileAction.ProfileUPdatedVaild -> {

                if (updatPhoneFlag == 1) {
                    if (verified_phone.isNullOrEmpty() || verified_phone == null) {

                        mViewModel.checkPhone(countryCode, binding.etPhone.text.toString())

                    } else {
                        if (verified_phone == data?.phone && verified_countryCode == data?.countryCode) {
                            mViewModel.updateProfile(action.data)


                        } else {
                            mViewModel.checkPhone(countryCode, binding.etPhone.text.toString())

                        }
                    }


                } else {
                    mViewModel.updateProfile(action.data)
                }
            }

            is ProfileAction.ShowPhoneConfirmed -> {
                showProgress(false)
                if (action.data.exists == 1) {
                    showToast(resources.getString(R.string.phone_exist_already))
                } else {

                    if (verified_phone.isNullOrEmpty() || verified_phone == null) {
                        CheckOtpSheetFragment.newInstance(countryCode,
                            binding.etPhone.text.toString(),
                            object : OnPhoneCheckedWithOtp {
                                override fun onClick(
                                    country_code: String, phone: String, verifed: Boolean
                                ) {
                                    verified_phone = phone
                                    verified_countryCode = country_code
                                    mViewModel.data?.let { mViewModel.updateProfile(it) }
                                }


                            }).show(childFragmentManager, "CheckOtpSheetFragment")

                    } else {
                        if (verified_phone == mViewModel.phone && verified_countryCode == mViewModel.country_code) {
                            mViewModel.updateProfile(mViewModel.data!!)
                        } else {
                            CheckOtpSheetFragment.newInstance(countryCode,
                                binding.etPhone.text.toString(),
                                object : OnPhoneCheckedWithOtp {
                                    override fun onClick(
                                        country_code: String, phone: String, verifed: Boolean
                                    ) {
                                        verified_phone = phone
                                        verified_countryCode = country_code
                                        mViewModel.data?.let { mViewModel.updateProfile(it) }
                                    }


                                }).show(childFragmentManager, "CheckOtpSheetFragment")

                        }

                    }
                }
            }

            else -> {

            }
        }
    }

    fun searchInCiteies(id: String, list: ArrayList<CitesItemsResponse>): CitesItemsResponse? {
        for (i in list) {
            if (id == i.id) return i

        }
        return null
    }

    private fun openCountriesDialog(data: ArrayList<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                (item?.id)?.let { countryId = it.toString() }
                binding.etCoutry.text = item?.name
                binding.etCity.text = ""
                cityID = ""

            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }

    var balance: Double? = null
    private fun showAddBalanceSheetFragment() {
        AddBalanceSheetFragment.newInstance(object : OnClickAddBalance {
            override fun onClick(amount: String) {
                balance = amount.toDoubleOrNull()
                balance?.let {
                    val configData = generatePaytabsConfigurationDetails()

                    configData?.let { it1 ->
                        PaymentSdkActivity.startCardPayment(
                            requireActivity(), it1, this@ProfileFragment
                        )
                    }
                }

            }


        }).show(childFragmentManager, AddBalanceSheetFragment::class.java.canonicalName)
    }

    fun openCitiesDialog(data: ArrayList<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                (item?.id)?.let { cityID = it }
                binding.etCity.text = item?.name
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }

    private fun showData(data: ProfileResponse) {
        binding.lytData.isVisible = true
        this.data = data
        // PrefsHelper.saveUserData(data)
        binding.etUserName.setText(data.name)
        binding.tvNameTitle.text = data.name
        binding.etCity.text = data.cityName
        binding.etCoutry.text = data.countryName
        binding.etLocation.text = data.address
        binding.etEmail.setText(data.email)
        binding.etPhone.setText(data.phone)
        binding.tvBalance.text = data.balance?.toDoubleOrNull()?.roundTo(2).toString() + " " + resources.getString(R.string.sr)
        this@ProfileFragment.lat = data.lat?.toDoubleOrNull()
        this@ProfileFragment.long = data.lon?.toDoubleOrNull()
        binding.ivProfile.loadImage(
            data.photo,
            placeHolderImage = R.drawable.empty_user,
            error_img = R.drawable.empty_user,
            isCircular = true
        )

        countryCode = data.countryCode.toString()
        data.cityId?.let {
            cityID = it
        }
        data.countryId?.let {
            countryId = it
        }
        mViewModel.getAllCitiesByCountryId(countryId, getCurrentCountryName)
        try {
            var country_code = data.countryCode.substring(1, data.countryCode.length).toString()
            country_code.toInt()?.let { binding.countryCodePicker.setCountryForPhoneCode(it) }

        } catch (e: Exception) {

        }


    }

    var updatPhoneFlag = 0

    private fun onClick() {
        binding.countryCodePicker.setOnCountryChangeListener(this)
        binding.btnDelete.setOnClickListener {
            showDeletBotttomSheetFragment()
        }
        binding.btnChangePass.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)
        }

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        binding.btnAddWallet.setOnClickListener {
            showAddBalanceSheetFragment()
        }
        binding.btnEdit.setOnClickListener {
            if (state == 0) stateEditProfile()
            else {
                if (binding.etPhone.text.toString() == PrefsHelper.getUserData()?.phone) updatPhoneFlag =
                    0
                else updatPhoneFlag = 1
                mViewModel.validateUpdateProfile(
                    binding.etUserName.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.etEmail.text.toString(),
                    countryCode,
                    countryId.toString(),
                    cityID.toString(),
                    lat,
                    long,
                    photo = image,
                    binding.etLocation.text.toString()
                )


            }
        }
        binding.etCoutry.setOnClickListener {
            if (state == 1) {
                mViewModel.getAllCountry(getAllCountries)
            }
        }

        binding.etCity.setOnClickListener {
            if (state == 1) {
                if (countryId == "") showToast(resources.getString(R.string.choose_country_first))
                else mViewModel.getAllCitiesByCountryId(
                    countryId, getAllCountries
                )
            }
        }
        binding.etLocation.setOnClickListener {
            checkLocation()
        }
        binding.ivEdit.setOnClickListener {

            pickImage()
        }
    }

    private fun openMaps() {
        MapBottomSheet.newInstance(object : onLocationClick {
            override fun onClick(lat: Double?, long: Double?, address: AddressParams?) {
                this@ProfileFragment.lat = lat
                this@ProfileFragment.long = long
                //   this@ProfileFragment.address=address
                if (!address.isNull()) {
                    binding.etLocation.visibility = View.VISIBLE
                    binding.etLocation.text = address?.address.toString()
                } else {
                    binding.etLocation.visibility = View.GONE
                }
            }
        }).show(childFragmentManager, MapBottomSheet::class.java.canonicalName)
    }

    private fun checkLocation() {
        if (permissionManager.hasAllLocationPermissions()) {
            checkIfLocationEnabled()
        } else {
            permissionsLauncher?.launch(permissionManager.getAllLocationPermissions())
        }
    }

    private val locationSettingLauncher = openLocationSettingsResultLauncher {
        checkIfLocationEnabled()
    }

    private fun checkIfLocationEnabled() {
        if (locationManager.isLocationEnabled()) {
            openMaps()
        } else {
            activity?.let { locationManager.buildAlertMessageNoGps(it, locationSettingLauncher) }
        }
    }


    private val permissionsLauncher = requestAppPermissions { allIsGranted, _ ->
        if (allIsGranted) {
            checkIfLocationEnabled()
        } else {
            Toast.makeText(
                activity, getString(R.string.not_all_permissions_accepted), Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.btnAddWallet.paintFlags = binding.btnAddWallet.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.ivProfile.visibility = View.GONE
                binding.lytImg.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.lytImg.visibility = View.VISIBLE
                binding.ivProfile.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
        binding.swiperefresh.setOnRefreshListener {
            mViewModel.getProfile()
            if (binding.swiperefresh != null) binding.swiperefresh.isRefreshing = false
        }
    }

    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun showDeletBotttomSheetFragment() {
        DeleteAccountSheetFragment.newInstance(object : OnClick {
            override fun onClick(choice: String) {
                if (choice.equals(Constants.YES)) {

                    mViewModel.deleteAccount()


                } else {

                }
            }


        }).show(childFragmentManager, DeleteAccountSheetFragment::class.java.canonicalName)
    }

    fun stateShowData() {
        state = 0
        binding.etUserName.isEnabled = false
        binding.etPhone.isEnabled = false
        binding.etCity.isEnabled = false
        binding.etCoutry.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.etLocation.isEnabled = false
        binding.ivEdit.isVisible = false
        binding.countryCodePicker.isEnabled = false
        binding.etUserName.setTextColor(resources.getColor(R.color.grey_700))
        binding.etPhone.setTextColor(resources.getColor(R.color.grey_700))
        binding.etCity.setTextColor(resources.getColor(R.color.grey_700))
        binding.etCoutry.setTextColor(resources.getColor(R.color.grey_700))
        binding.etEmail.setTextColor(resources.getColor(R.color.grey_700))
        binding.etLocation.setTextColor(resources.getColor(R.color.grey_700))
        binding.btnEdit.text = resources.getText(R.string.edit)
    }

    fun stateEditProfile() {
        state = 1
        binding.etUserName.isEnabled = true
        binding.etPhone.isEnabled = true
        binding.etCity.isEnabled = true
        binding.etCoutry.isEnabled = true
        binding.etEmail.isEnabled = true
        binding.etLocation.isEnabled = true
        binding.ivEdit.isVisible = true
        binding.countryCodePicker.isEnabled = true
        binding.etUserName.setTextColor(Color.BLACK)
        binding.etPhone.setTextColor(Color.BLACK)
        binding.etCity.setTextColor(Color.BLACK)
        binding.etCoutry.setTextColor(Color.BLACK)
        binding.etEmail.setTextColor(Color.BLACK)
        binding.etLocation.setTextColor(Color.BLACK)
        binding.btnEdit.text = resources.getText(R.string.save)
    }

    private val imagePermissionLauncherResult = requestAppPermissions { allIsGranted, _ ->
        if (allIsGranted) {
            FileManager.pickOneImage(this, selectImageFromGalleryResult)
        } else showToast(getString(R.string.not_all_permissions_accepted))
    }

    private fun pickImage() {
        if (permissionManager.hasAllFilePickerPermissions()) {
            FileManager.pickOneImage(this, selectImageFromGalleryResult)
        } else {
            imagePermissionLauncherResult?.launch(permissionManager.getAllImagePermissions())
        }
    }

    var image: File? = null

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                FileManager.from(requireActivity(), it)?.let { file ->
                    image = file

                    binding.ivProfile.loadImage(file, isCircular = true)
                }
            }
        }

    override fun onCountrySelected() {
        countryCode = "+" + binding.countryCodePicker.selectedCountryCode
    }

    override fun onError(error: PaymentSdkError) {
        showToast(error.msg.toString())
    }

    override fun onPaymentCancel() {
        showToast(resources.getString(R.string.cancel))
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {

        Log.i("TAG", "Did payment success?: ${paymentSdkTransactionDetails.isSuccess}")
        var token = paymentSdkTransactionDetails.token
        var transRef = paymentSdkTransactionDetails.transactionReference
        Toast.makeText(
            requireContext(),
            paymentSdkTransactionDetails.paymentResult?.responseMessage ?: "PaymentFinish",
            Toast.LENGTH_SHORT
        ).show()
        transRef?.let {
            mViewModel.updateBalance(
                UpdateBalanceParam(
                    balance.toString(), token.toString(), it
                )
            )
        }
    }

    private fun generatePaytabsConfigurationDetails(selectedApm: PaymentSdkApms? = null): PaymentSdkConfigurationDetails? {
        val clientKey = "CGKMDK-VMKR6H-PT7NRM-GMPB7G"
        val serverKey = "SDJNLWHD6L-JH9GTMR2KT-ZDHLDDW9DN"
        val profileId = "44353"
        var locale = PaymentSdkLanguageCode.EN
        if (PrefsHelper.getLanguage() == Constants.AR) locale =
            PaymentSdkLanguageCode.AR/*Or PaymentSdkLanguageCode.AR*/
        val transactionTitle = "Test Pay"
        val cartDesc = "Cart description"
        val currency = "EGP"
        val amount = balance
        val merchantCountryCode = "SA"
        val billingData = PaymentSdkBillingDetails(
            "City",
            "SA",
            "email1@domain.com",
            "name name",
            "+966568595106",
            "121321",
            "address street",
            ""
        )
        val shippingData = PaymentSdkShippingDetails(
            "City", "SA", "test@test.com", "name1 last1", "+966568595106", "3510", "street2", ""
        )
        amount?.let {
            val configData = PaymentSdkConfigBuilder(
                profileId, serverKey, clientKey, it, currency
            ).setCartDescription(cartDesc).setLanguageCode(locale).setBillingData(billingData)
                .setMerchantCountryCode(merchantCountryCode)
                .setTransactionType(PaymentSdkTransactionType.SALE)
                .setTransactionClass(PaymentSdkTransactionClass.ECOM)
                //   .setShippingData(shippingData)
                .setTokenise(PaymentSdkTokenise.NONE) //Check other tokenizing types in PaymentSdkTokenise
                .setCartId(PrefsHelper.getUserData()?.id).showBillingInfo(false)
                .showShippingInfo(false).forceShippingInfo(false).setScreenTitle(transactionTitle)


            if (selectedApm != null) configData.setAlternativePaymentMethods(listOf(selectedApm))/*Check PaymentSdkApms for more payment options*/
            return configData.build()
        }
        return null
    }


}