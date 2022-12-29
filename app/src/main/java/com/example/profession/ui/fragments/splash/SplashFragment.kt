package com.example.profession.ui.fragments.splash

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.nadifalaundries.data.repositoy.PrefsHelper
import com.example.profession.R
import com.example.profession.databinding.FragmentSplashBinding
import com.example.profession.base.BaseFragment
import com.example.profession.ui.activity.MainActivity
import com.example.profession.util.ext.showActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {


    override fun onFragmentReady() {
      lifecycleScope.launch {
          delay(2000)
        if(PrefsHelper.getToken().isNullOrBlank()){
            if(PrefsHelper.getIsloggedInBefore()) {
                findNavController().navigate(R.id.loginFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
            }else{
                findNavController().navigate(R.id.walkThrougthFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.walkThrougthFragment, true).build())
            }


        } else{
            showActivity(MainActivity::class.java, clearAllStack = true)
           }


          //   findNavController().navigate(R.id.la)
        }
    }
}