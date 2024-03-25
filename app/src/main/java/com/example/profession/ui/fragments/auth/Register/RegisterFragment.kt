package com.example.profession.ui.fragments.auth.Register

import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
  import com.example.profession.R
import com.example.profession.databinding.FragmentRegisterBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.Param.AddressParams
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.ui.adapter.CitesListener
import com.example.profession.ui.dialog.CategoriesDialog
import com.example.profession.ui.dialog.CheckOtpSheetFragment
import com.example.profession.ui.dialog.OnPhoneCheckedWithOtp
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.auth.AuthViewModel
import com.example.profession.ui.fragments.map.MapBottomSheet
import com.example.profession.ui.fragments.map.onLocationClick
import com.example.profession.util.*
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.isNull
import com.example.profession.util.ext.showActivity
import com.hbb20.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>()  ,    CountryCodePicker.OnCountryChangeListener {
    private var countryCode: String = "+966"
    private val mViewModel: AuthViewModel by viewModels()
    var verified_countryCode = ""
    var verified_phone: String? = null

    var cityID: String = ""
    var countryId: String = ""
    var lat: Double? = null
    var long: Double? = null
    var address: String? = null
    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var locationManager: WWLocationManager
    override fun onFragmentReady() {
        onClick()
        setupUi()

        mViewModel.apply {
            observe(viewState) {
                handleViewState(it)
            }
        }
    }

    private fun handleViewState(action: AuthAction) {
        when (action) {
            is AuthAction.ShowLoading -> {
                showProgress(action.show)
                if (action.show) {
                    hideKeyboard()
                }
            }
            is AuthAction.RegisterationSuccess -> {
                showProgress(false)
                showActivity(MainActivity::class.java, clearAllStack = true)
            }
            is AuthAction.ShowRegisterVaildationSucess -> {
                mViewModel.checkPhone(   action.data.countryCode.toString(),
                    action.data.phone.toString(),)
                showProgress(false)
            }

            is AuthAction.ShowPhoneConfirmed -> {
                showProgress(false)
                if (action.data?.exists == 1) {
                    showToast(resources.getString(R.string.phone_exist_already))
                } else {

                    if (verified_phone.isNullOrEmpty() || verified_phone == null) {
                        CheckOtpSheetFragment.newInstance(
                          mViewModel.param?.countryCode.toString(),
                          mViewModel.param?.phone.toString(),
                            object : OnPhoneCheckedWithOtp {
                                override fun onClick(
                                    country_code: String, phone: String, verifed: Boolean
                                ) {
                                    verified_phone = phone
                                    verified_countryCode = country_code
                                    mViewModel.param?.let { mViewModel.register(it) }
                                }
                            }).show(
                            childFragmentManager, "CheckOtpSheetFragment"
                        )

                    } else {
                        if (verified_phone == mViewModel.param?.phone && verified_countryCode == mViewModel.param?.countryCode) {
                            mViewModel.register(mViewModel.param!!)
                        } else {
                            mViewModel.param?.phone?.let {
                                CheckOtpSheetFragment.newInstance(
                                    mViewModel.param?.countryCode.toString(),
                                    it,
                                    object : OnPhoneCheckedWithOtp {
                                        override fun onClick(
                                            country_code: String, phone: String, verifed: Boolean
                                        ) {
                                            verified_phone = phone
                                            verified_countryCode = country_code
                                            mViewModel.register(mViewModel.param!!)
                                        }
                                    }).show(
                                    childFragmentManager, "CheckOtpSheetFragment"
                                )
                            }
                        }
                    }
                }
            }

            is AuthAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)
                 }

            is AuthAction.ShowAllCities -> {
                showProgress(false)
                action.data.cities?.let { openCitiesDialog(it) }
            }

            is AuthAction.ShowAllCountry -> {
                showProgress(false)
                action.data.countries?.let { openCountriesDialog(it) }
            }
            else -> {

            }
        }
    }

    private fun openCountriesDialog(data: ArrayList<CitesItemsResponse>) {
        binding.etCity.text = ""
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                binding.etCoutry.text = item?.name
                (item?.id)?.let { countryId = it }
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }


    fun openCitiesDialog(data: ArrayList<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                binding.etCity.text = item?.name
                (item?.id)?.let { cityID = it }
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }


    private fun setupUi() {
        binding.btnSignIn.paintFlags = binding.btnSignIn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.btnVisitorLogin.paintFlags = binding.btnVisitorLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.terms.text =
            HtmlCompat.fromHtml(getString(R.string.some_text), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun onClick() {
binding.countryCodePicker.setOnCountryChangeListener(this)

        binding.btnRegister.setOnClickListener {
            mViewModel.validateRegisteration(
                binding.etUserName.text.toString(),  binding.etPhone.text.toString(), binding.etEmail.text.toString() , countryCode,
                countryId.toString(),
                cityID.toString(),
                binding.etPassword.text.toString(), lat,long, address

            )
        }
            binding.btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.loginFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
        }


        binding.btnVisitorLogin.setOnClickListener {
         PrefsHelper.saveUserData(null)
            showActivity(MainActivity::class.java, clearAllStack = true)
        }
        binding.etCity.setOnClickListener {
            if (countryId == "")  showToast(resources.getString(R.string.choose_country_first))
                    else  mViewModel.getAllCitiesByCountryId(countryId.toString())
        }
        binding.etCoutry.setOnClickListener {
            mViewModel.getAllCountry()
        }
        binding.etLocation.setOnClickListener {
            checkLocation()
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.terms.setOnClickListener {
            findNavController().navigate(R.id.rightsAndTermsFragment2)
         }
    }
    private fun openMaps() {
        MapBottomSheet.newInstance(object : onLocationClick {
            override fun onClick(lat:Double? ,long :Double? , address :AddressParams?) {
                this@RegisterFragment.lat =lat
                this@RegisterFragment.long=long
                 if(!address.isNull()){
                    binding.etLocation.visibility= View.VISIBLE
                    this@RegisterFragment.address= address?.address.toString()
                     binding.etLocation.text = address?.address.toString()
                }else{
                    binding.etLocation.visibility= View.GONE
                }
            }
        }
        ).show(childFragmentManager, MapBottomSheet::class.java.canonicalName)
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
                activity,
                getString(R.string.not_all_permissions_accepted),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCountrySelected() {
        countryCode ="+"+ binding.countryCodePicker.selectedCountryCode

     }

}