package com.example.profession.ui.fragments.profile


import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.profession.R
import com.example.profession.databinding.FragmentProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.data.dataSource.response.CitesItemsResponse
import com.example.profession.data.dataSource.response.ProfileResponse
import com.example.profession.ui.adapter.CitesListener
import com.example.profession.ui.dialog.CategoriesDialog
import com.example.profession.ui.dialog.DeleteAccountSheetFragment
import com.example.profession.ui.dialog.OnClick
import com.example.profession.ui.fragments.auth.AuthAction
import com.example.profession.ui.fragments.map.MapBottomSheet
import com.example.profession.ui.fragments.map.onLocationClick
import com.example.profession.util.*
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.loadImage
import com.google.android.material.appbar.AppBarLayout
import com.hbb20.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>()   ,    CountryCodePicker.OnCountryChangeListener {
    private var countryCode: String = "+966"
    private lateinit var parent: MainActivity
    private val mViewModel: ProfileViewModel by viewModels()
    var cityID: Int = -1
    var countryId: Int = -1

    var lat: Double? = null
    var long: Double? = null
    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var locationManager: WWLocationManager
var state =0 // show data  1->edit
    override fun onFragmentReady() {
        onClick()
stateShowData()
        setupUi()
        mViewModel.apply {
            get_profile()
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
            is ProfileAction.ShowUpdatesProfile -> {
                showProgress(false)
showToast(action.message)
                stateShowData()
            }
            is ProfileAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

        is ProfileAction.ShowAllCities -> {
            showProgress(false)
            openCitiesDialog(action.data)
        }

        is ProfileAction.ShowAllCountry -> {
            showProgress(false)
            openCountriesDialog(action.data)
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


        else -> {

        }
    }
}

private fun openCountriesDialog(data: PagingData<CitesItemsResponse>) {
    CategoriesDialog.newInstance(object : CitesListener {
        override fun onOrderClicked(item: CitesItemsResponse?) {
            (item?.id)?.let { countryId = it }
            binding.etCoutry.setText(item?.name)
        }


    }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
}


fun openCitiesDialog(data: PagingData<CitesItemsResponse>) {
    CategoriesDialog.newInstance(object : CitesListener {
        override fun onOrderClicked(item: CitesItemsResponse?) {
            (item?.id)?.let { cityID = it }
            binding.etCity.setText(item?.name)
        }


    }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
}

    private fun showData(data: ProfileResponse) {
        binding.etUserName.setText(data.name)
        binding.tvNameTitle.setText(data.name)
        binding.etCity.setText(data.cityName)
        binding.etCoutry.setText(data.countryName)
        binding.etLocation.setText(data.countryName + "," + data.cityName)
        binding.etEmail.setText(data.email)
        binding.etPhone.setText(data.phone)
        countryCode= data.countryCode.toString()
       data.cityId?.let {
           cityID=it
        }
        data.countryId?.let {
            countryId=it
        }
        // binding.countryCodePicker.selectedCountryCode=data.countryCode

    }


    private fun onClick() {
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
        binding.btnEdit.setOnClickListener {
            if(state==0)stateEditProfile()
            else mViewModel.validateUpdateProfile(binding.etUserName.text.toString(),binding.etPhone.text.toString(),binding.etEmail.text.toString(),
                countryCode,
                countryId.toString(),
                cityID.toString(),
                lat,long , photo = null )
        }
        binding.etCoutry.setOnClickListener {
            if(state==1) mViewModel.getAllCountry()
        }

        binding.etCity.setOnClickListener {
            if(state==1) {
                if (countryId == -1) showToast(resources.getString(R.string.choose_country_first))
                else mViewModel.getAllCitiesByCountryId(countryId.toString())
            }
        }
        binding.etLocation.setOnClickListener {
            checkLocation()
        }
        binding.ivEdit.setOnClickListener{

            pickImage()
        }
    }
    private fun openMaps() {
        MapBottomSheet.newInstance(object : onLocationClick {
            override fun onClick(lat:Double? ,long :Double? , address :String?) {
                this@ProfileFragment.lat =lat
                this@ProfileFragment.long=long
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


    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.btnChangePass.setPaintFlags(binding.btnChangePass.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.getTotalScrollRange()) {
                // If collapsed, then do this
                binding.ivProfile.setVisibility(View.GONE);
                binding.lytImg.setVisibility(View.GONE);
                binding.btnChangePass.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.lytImg.setVisibility(View.VISIBLE);
                binding.ivProfile.setVisibility(View.VISIBLE);
                binding.btnChangePass.setVisibility(View.VISIBLE);
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
        binding.swiperefresh .setOnRefreshListener {
            mViewModel.get_profile()
            if (binding.swiperefresh != null) binding.swiperefresh.isRefreshing =
                false
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
    fun stateShowData(){
state=0
binding.etUserName.isEnabled= false
        binding.etPhone.isEnabled= false
        binding.etCity.isEnabled= false
        binding.etCoutry.isEnabled= false
        binding.etEmail.isEnabled= false
        binding.etLocation.isEnabled= false
binding.ivEdit.isVisible=false
        binding.etUserName.setTextColor(resources.getColor(R.color.grey_700))
        binding.etPhone.setTextColor(resources.getColor(R.color.grey_700))
        binding.etCity.setTextColor(resources.getColor(R.color.grey_700))
        binding.etCoutry.setTextColor(resources.getColor(R.color.grey_700))
        binding.etEmail.setTextColor(resources.getColor(R.color.grey_700))
        binding.etLocation.setTextColor(resources.getColor(R.color.grey_700))
    }
    fun stateEditProfile(){
   state=1
        binding.etUserName.isEnabled= true
        binding.etPhone.isEnabled= true
        binding.etCity.isEnabled= true
        binding.etCoutry.isEnabled= true
        binding.etEmail.isEnabled= true
        binding.etLocation.isEnabled= true
        binding.ivEdit.isVisible=true

        binding.etUserName.setTextColor(Color.BLACK)
        binding.etPhone.setTextColor(Color.BLACK)
        binding.etCity.setTextColor(Color.BLACK)
        binding.etCoutry.setTextColor(Color.BLACK)
        binding.etEmail.setTextColor(Color.BLACK)
        binding.etLocation.setTextColor(Color.BLACK)
    }

    private val imagePermissionLauncherResult = requestAppPermissions { allIsGranted, _ ->
        if (allIsGranted) {
            FileManager.pickOneImage(this, selectImageFromGalleryResult)
        } else
            showToast(getString(R.string.not_all_permissions_accepted))
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

                    binding.ivProfile.loadImage(file)
                }
            }
        }

    override fun onCountrySelected() {
countryCode= binding.countryCodePicker.selectedCountryCode    }
}