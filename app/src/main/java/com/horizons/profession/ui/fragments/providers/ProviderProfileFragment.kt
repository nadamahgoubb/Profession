package com.horizons.profession.ui.fragments.providers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentProviderProfileBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.response.Providers
import com.horizons.profession.ui.adapter.ProvidersSubServiceAdapter
import com.horizons.profession.ui.fragments.subService.CreateOrdersAction
import com.horizons.profession.ui.fragments.subService.CreateOrdersViewModel
import com.horizons.profession.util.Constants
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.init
import com.horizons.profession.util.ext.loadImage
import com.horizons.profession.util.ext.roundTo
import com.horizons.profession.util.observe
import com.google.android.material.appbar.AppBarLayout

class ProviderProfileFragment : BaseFragment<FragmentProviderProfileBinding>() {
    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter: ProvidersSubServiceAdapter
    var providerData: Providers? = null
    override fun onFragmentReady() {
        onClick()
        initAdapter()
        setupScrollUi()
        mViewModel.apply {
            observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
        arguments?.getParcelable<Providers>(Constants.PROVIDERS)?.let {
            providerData = it
            showData()
        }
    }

    private fun initAdapter() {
        adapter = ProvidersSubServiceAdapter()
        binding.rvSeriveDetails.init(requireContext(), adapter, 1)
    }

    private fun showData() {
        providerData?.let {
            binding.tvName.text = it.name
            binding.tvDesc.text = it.previousExperience
            binding.ivProfile.loadImage(it.photo, isCircular = true)
            binding.tvRate.text = it.totalRate?.toDoubleOrNull()?.roundTo(2) .toString()

            binding.tvLocation.text = it.address.toString()
            binding.tvRateCounts.text = "(" + it.countReviews + ")"

            binding.itemDistance.tvTitle.text = resources.getText(R.string.distance_between)
            binding.tvJob.text = it.service?.name
            binding.itemDistance.tvData.text = it.distance.toString().toString()+ resources.getString(R.string.km)
            binding.itemDistance.ivImg.loadImage(resources.getDrawable(R.drawable.ic_location))

            binding.itemExperience.tvTitle.text = resources.getText(R.string.ecperience)
            binding.itemExperience.tvData.text = it.yearsExperience.toString()
            binding.itemExperience.ivImg.loadImage(resources.getDrawable(R.drawable.ic_experience))

            binding.itemCost.tvTitle.text = resources.getText(R.string.hours_cost)
            binding.itemCost.tvData.text = it.hourPrice.toString()+resources.getString(R.string.sr)
            binding.itemCost.ivImg.loadImage(resources.getDrawable(R.drawable.ic_cost))
            adapter.list = it.subServices
            adapter.notifyDataSetChanged()
            binding.ivCall.setOnClickListener {
              call(providerData?.phone.toString())
            }
        }

    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }


        binding.btnOrder.setOnClickListener {
            mViewModel.selectedProviders.clear()
            providerData?.let { it1 -> mViewModel.selectedProviders.add(it1) }
            findNavController().navigate(R.id.chooseTimeFragment)
        }
        binding.lytRate.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.PROVIDER_ID, providerData?.id)
            findNavController().navigate(R.id.reviewsFragment, bundle )

         }
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

            else -> {

            }
        }
    }

    private fun setupScrollUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == binding.appBarLayout.totalScrollRange) {
                // If collapsed, then do this
                binding.lytImg.visibility = View.GONE
                binding.ivProfile.visibility = View.GONE
                binding.ivCall.visibility = View.GONE
            } else if (verticalOffset == 0) {
                binding.lytImg.visibility = View.VISIBLE
                binding.ivProfile.visibility = View.VISIBLE
                binding.ivCall.visibility = View.VISIBLE
            } else {
                // Somewhere in between
                // Do according to your requirement
            }

        })
    }

    fun call(tel: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + tel)
        startActivity(dialIntent)
    }

}