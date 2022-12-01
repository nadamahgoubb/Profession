package com.example.profession.ui.fragments

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.profession.R
import com.example.profession.data.PrefsHelper
import com.example.profession.databinding.FragmentSplashBinding
import com.example.profession.ui.activity.MainActivity
import com.example.profession.ui.base.BaseFragment
import com.example.profession.util.ext.showActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {


    override fun onFragmentReady() {
      lifecycleScope.launch {
          delay(2000)
          //  if(PrefsHelper.getToken().isNullOrBlank()){
                findNavController().navigate(R.id.walkThrougthFragment,null,
                    NavOptions.Builder().setPopUpTo(R.id.walkThrougthFragment, false).build())
        //    } else{
        //        showActivity(MainActivity::class.java, clearAllStack = true)
        //    }


           //    findNavController().navigate(R.id.action_splashFragment_to_languageFragment)
        }
    }
}