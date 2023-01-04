package com.example.profession.ui.fragments.auth.Register

import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.profession.R
import com.example.profession.databinding.FragmentRegisterBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.ui.adapter.CitesListener
import com.example.profession.ui.adapter.CitesPagingAdapter
import com.example.profession.ui.adapter.ServicesHomeAdapter
import com.example.profession.ui.adapter.SliderHomeAdapter
import com.example.profession.ui.dialog.CategoriesDialog
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.auth.AuthViewModel
import com.example.profession.ui.fragments.map.MapBottomSheet
import com.example.profession.ui.fragments.map.onLocationClick
import com.example.profession.ui.listener.ServiceOnClickListener
import com.example.profession.util.*
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.showActivity
import com.hbb20.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>()  ,    CountryCodePicker.OnCountryChangeListener {
    private var countryCode: String = "+966"
    private val mViewModel: AuthViewModel by viewModels()
    var cityID: Int = -1
    var countryId: Int = -1
    var lat: Double? = null
    var long: Double? = null
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

            is AuthAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }
            is AuthAction.ShowAllCities -> {
                showProgress(false)
                openCitiesDialog(action.data)
            }

            is AuthAction.ShowAllCountry -> {
                showProgress(false)
                openCountriesDialog(action.data)
            }
            else -> {

            }
        }
    }

    private fun openCountriesDialog(data: PagingData<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                binding.etCoutry.setText(item?.name)
                (item?.id)?.let { countryId = it }
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }


    fun openCitiesDialog(data: PagingData<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
                binding.etCity.setText(item?.name)
                (item?.id)?.let { cityID = it }
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }


    private fun setupUi() {
        binding.btnSignIn.setPaintFlags(binding.btnSignIn.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        binding.terms.text =
            HtmlCompat.fromHtml(getString(R.string.some_text), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun onClick() {
/*        name: String,
        phone: String,
        email: String,
        country_code: String?,
        countryId:String,
        cityId:String,
        pass: String,
        lat: Double?,
        lon: Double?,*/

        binding.btnRegister.setOnClickListener {
            mViewModel.validateRegisteration(
                binding.etUserName.text.toString(),  binding.etPhone.text.toString(), binding.etEmail.text.toString() , countryCode,
                countryId.toString(),
                cityID.toString(),
                binding.etPassword.text.toString(), lat,long

            )

        }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)

        }
        binding.etCity.setOnClickListener {
            if (countryId == -1)  showToast(resources.getString(R.string.choose_country_first))
                    else  mViewModel.getAllCitiesByCountryId(countryId.toString())
        }
        binding.etCoutry.setOnClickListener {
            mViewModel.getAllCountry()
        }
        binding.etLocation.setOnClickListener {
            checkLocation()
        }
    }
    private fun openMaps() {
        MapBottomSheet.newInstance(object : onLocationClick {
            override fun onClick(lat:Double? ,long :Double? , address :String?) {
                this@RegisterFragment.lat =lat
                this@RegisterFragment.long=long
             //   this@RegisterFragment.address=address
                if(!address.isNullOrEmpty()){
                    binding.etLocation.visibility= View.VISIBLE
                    binding.etLocation.setText( address.toString())
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
        countryCode = binding.countryCodePicker.selectedCountryCode

        Toast.makeText(activity, "Country Code " + countryCode, Toast.LENGTH_SHORT).show()
    }

}