package com.horizons.profession.ui.fragments.reviews

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.horizons.profession.databinding.FragmentReviewsBinding
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.data.dataSource.response.Reviews
import com.horizons.profession.ui.adapter.ReviewsAdapter
import com.horizons.profession.ui.fragments.subService.CreateOrdersAction
import com.horizons.profession.ui.fragments.subService.CreateOrdersViewModel
import com.horizons.profession.util.Constants
import com.horizons.profession.util.ext.hideKeyboard
import com.horizons.profession.util.ext.init
import com.horizons.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {
    private var providerId: String? = null
    private lateinit var parent: MainActivity
    private val mViewModel: CreateOrdersViewModel by activityViewModels()
    lateinit var adapter: ReviewsAdapter
    override fun onFragmentReady() {
        initAdapter()
        onClick()

        setupUi()
        mViewModel.apply {
         providerId?.let {   getProviderReview(it)}
            observe(mViewModel.viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefreshHome.setOnRefreshListener {
            providerId?.let { mViewModel.  getProviderReview(it)}
             if (binding.swiperefreshHome != null) binding.swiperefreshHome.isRefreshing = false
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
            is CreateOrdersAction.ShowProviderReview -> {
                showProgress(false)
                loadReviews(action.data.Reviews)

            }

            is CreateOrdersAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            else -> {

            }
        }
    }

    private fun loadReviews(reviews: ArrayList<Reviews>) {
        if (reviews.size > 0) {
            binding.lytEmptyState.isVisible= false
            adapter.list = reviews
            adapter.notifyDataSetChanged()
        }else{
            binding.lytEmptyState.isVisible= true
        }
    }
    private fun initAdapter() {
        adapter = ReviewsAdapter()
        binding.rvReviews.init(requireContext(), adapter, 2)
    }

    private fun onClick() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnGoProviders.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(false)
        parent.showSideNav(false)
providerId = arguments?.getString(Constants.PROVIDER_ID)
    }

}