package com.horizons.profession.ui.fragments.splash

import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import com.horizons.profession.R
import com.horizons.profession.databinding.FragmentSplashBinding
import com.horizons.profession.base.BaseFragment
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.util.ext.showActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun onFragmentReady() {
    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(800)
            fadeAnimation()
            if(PrefsHelper.getToken().isNullOrBlank()){
                if(PrefsHelper.getIsloggedInBefore()) {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    //  ,null, NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build())
                }else{
                    PrefsHelper.setloggedInBefore(true)
                    findNavController().navigate(R.id.action_splashFragment_to_walkThrougthFragment)
                    // ,null, NavOptions.Builder().setPopUpTo(R.id.walkThrougthFragment, true).build())
                }


            } else{
                showActivity(MainActivity::class.java, clearAllStack = true)
            }
        }
    }

    private fun fadeAnimation(){
        val fadeOut: Animation = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.startOffset = 1000
        fadeOut.duration = 800
        val animation = AnimationSet(false)
        animation.addAnimation(fadeOut)
        animation.addAnimation(fadeOut)
        binding.root.animation = animation
    }
}