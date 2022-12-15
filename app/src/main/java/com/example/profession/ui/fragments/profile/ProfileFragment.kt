package com.example.profession.ui.fragments.profile


import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.databinding.FragmentProfileBinding
import com.example.profession.ui.activity.AuthActivity
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.ui.dialog.DeleteAccountSheetFragment
import com.example.profession.ui.dialog.LoginFirstBotomSheetFragment
import com.example.profession.ui.dialog.OnClick
import com.example.profession.ui.dialog.OnClickLoginFirst
import com.example.profession.util.Constants


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

    setupUi()
}

private fun setupUi() {
    parent = requireActivity() as MainActivity
    parent.showBottomNav(true)
    parent.showSideNav(true)

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