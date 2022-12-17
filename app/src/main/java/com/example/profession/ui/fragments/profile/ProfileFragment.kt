package com.example.profession.ui.fragments.profile


import android.content.Intent
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.dialog.DeleteAccountSheetFragment
import com.example.profession.ui.dialog.OnClick
import com.example.profession.util.Constants
import com.google.android.material.appbar.AppBarLayout


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var parent: MainActivity
    override fun onFragmentReady() {
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
    setupUi()
}

private fun setupUi() {
    parent = requireActivity() as MainActivity
    parent.showBottomNav(true)
    parent.showSideNav(true)
    binding.btnChangePass.setPaintFlags( binding.btnChangePass.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (Math.abs(verticalOffset) ==   binding.appBarLayout.getTotalScrollRange()) {
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

}
    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
    private fun showDeletBotttomSheetFragment() {
        DeleteAccountSheetFragment.newInstance(object : OnClick {
            override fun onClick(choice: String) {
                if (choice.equals(Constants.YES)) {
                    var intent = Intent(activity, AuthActivity::class.java)
                    intent.putExtra(Constants.Start, Constants.login)
                    startActivity(intent)
                    activity?.finish()
                } else {

                }
            }


        }).show(childFragmentManager, DeleteAccountSheetFragment::class.java.canonicalName)
    }
}