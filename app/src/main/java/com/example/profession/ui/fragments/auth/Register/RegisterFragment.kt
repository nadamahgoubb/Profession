package com.example.profession.ui.fragments.auth.Register

import android.graphics.Paint
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
import com.example.profession.ui.listener.ServiceOnClickListener
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.showActivity
import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>()    {
    private val mViewModel: AuthViewModel by viewModels()
    var cityID: Int = -1
    var countryId: Int = -1

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
                (item?.id)?.let { countryId = it }
            }


        }, data).show(childFragmentManager, CategoriesDialog::class.java.canonicalName)
    }


    fun openCitiesDialog(data: PagingData<CitesItemsResponse>) {
        CategoriesDialog.newInstance(object : CitesListener {
            override fun onOrderClicked(item: CitesItemsResponse?) {
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

        binding.btnRegister.setOnClickListener {
            mViewModel.isValidParams(
                binding.etUserName.text.toString(), binding.etPassword.text.toString()
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
    }
}