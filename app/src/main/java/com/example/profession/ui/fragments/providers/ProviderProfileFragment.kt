package com.example.profession.ui.fragments.providers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.laundrydelivery.util.ext.roundTo
import com.example.profession.R
import com.example.profession.databinding.FragmentProviderProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
import com.example.profession.data.dataSource.response.Providers
import com.example.profession.ui.adapter.ProvidersSubServiceAdapter
import com.example.profession.ui.dialog.LoginFirstBotomSheetFragment
import com.example.profession.ui.dialog.OnClickLoginFirst
import com.example.profession.ui.fragments.subService.CreateOrdersAction
import com.example.profession.ui.fragments.subService.CreateOrdersViewModel
import com.example.profession.util.Constants
import com.example.profession.util.ext.hideKeyboard
import com.example.profession.util.ext.init
import com.example.profession.util.ext.loadImage
import com.example.profession.util.observe
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
            binding.tvName.setText(it.name)
            binding.tvDesc.setText(it.previousExperience)
            binding.ivProfile.loadImage(it.photo, isCircular = true)
            binding.tvRate.setText(it.totalRate.roundTo(2) .toString())

            binding.tvLocation.setText(it.address.toString())
            binding.tvServiceDetails.setText(it.address.toString())
            binding.tvRateCounts.setText("(" + it.countReviews + ")")

            binding.itemDistance.tvTitle.setText(resources.getText(R.string.distance_between))
            binding.tvJob .setText(it.service?.name)
            binding.itemDistance.tvData.setText(it.distance.toString())
            binding.itemDistance.ivImg.loadImage(resources.getDrawable(R.drawable.ic_location))

            binding.itemExperience.tvTitle.setText(resources.getText(R.string.ecperience))
            binding.itemExperience.tvData.setText(it.yearsExperience.toString())
            binding.itemExperience.ivImg.loadImage(resources.getDrawable(R.drawable.ic_experience))

            binding.itemCost.tvTitle.setText(resources.getText(R.string.hours_cost))
            binding.itemCost.tvData.setText(it.hourPrice.toString())
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
        binding.ivDawn.setOnClickListener {
            // ExpandAnimation.expand(binding.cardUp)
            //  ExpandAnimation.collapse(binding.cardDawn)
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
            if (Math.abs(verticalOffset) == binding.appBarLayout.getTotalScrollRange()) {
                // If collapsed, then do this
                binding.lytImg.setVisibility(View.GONE);
                binding.ivProfile.setVisibility(View.GONE);
                binding.ivCall.setVisibility(View.GONE);
            } else if (verticalOffset == 0) {
                binding.lytImg.setVisibility(View.VISIBLE);
                binding.ivProfile.setVisibility(View.VISIBLE);
                binding.ivCall.setVisibility(View.VISIBLE);
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
    private fun showLoginFirstBotomSheetFragment() {
        LoginFirstBotomSheetFragment.newInstance(object : OnClickLoginFirst {
            override fun onClick(choice: String) {
                if (choice.equals(Constants.YES)) {
                    var intent = Intent(activity, AuthActivity::class.java)
                    intent.putExtra(Constants.Start, Constants.login)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    findNavController().navigate(R.id.chooseTimeFragment)
                }
            }


        }).show(childFragmentManager, LoginFirstBotomSheetFragment::class.java.canonicalName)
    }
}