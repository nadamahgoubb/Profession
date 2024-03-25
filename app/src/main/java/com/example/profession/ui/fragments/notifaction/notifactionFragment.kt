package com.example.profession.ui.fragments.notifaction

 import androidx.core.view.isVisible
 import androidx.fragment.app.viewModels
import com.example.profession.databinding.FragmentNotifactionBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.base.BaseFragment
 import com.example.profession.data.dataSource.response.NotificationsItem
 import com.example.profession.ui.adapter.NotifactionAdapter
import com.example.profession.ui.fragments.profile.ProfileAction
import com.example.profession.ui.fragments.profile.ProfileViewModel
import com.example.profession.util.ext.hideKeyboard
 import com.example.profession.util.ext.init
 import com.example.profession.util.observe
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class notifactionFragment : BaseFragment<FragmentNotifactionBinding>() {
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var parent: MainActivity
    lateinit var adapter: NotifactionAdapter
    override fun onFragmentReady() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setupUi()
        mViewModel.apply {
            mViewModel.getNotifaction()
            observe(viewState) {
                handleViewState(it)
            }
        }
        binding.swiperefresh.setOnRefreshListener {
            mViewModel.getNotifaction()
            if (binding.swiperefresh != null) binding.swiperefresh.isRefreshing = false
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

            is ProfileAction.ShowFailureMsg -> action.message?.let {
                showToast(action.message)
                showProgress(false)

            }

            is ProfileAction.ShowNotifactions -> {
                showProgress(false)
                action.data.notifications?.let { loadNotifaction(it) }
            }

            else -> {}
        }
    }

    private fun loadNotifaction(notifications: ArrayList<NotificationsItem>) {
        if (notifications.size > 0) {
            binding.lytEmptyState.isVisible = false
            adapter.list = notifications
            adapter.notifyDataSetChanged()  }
        else {
            binding.lytEmptyState.isVisible = true
            adapter.list = arrayListOf()
            adapter.notifyDataSetChanged()
        }


    }


    private fun setupUi() {
        parent = requireActivity() as MainActivity
        parent.showBottomNav(true)
        parent.showSideNav(true)
        binding.ivMenu.setOnClickListener {
            parent.openDrawer()
        }
        adapter=NotifactionAdapter( )
        binding.rvOffersHome.init(requireContext(),adapter,2)
    }


}