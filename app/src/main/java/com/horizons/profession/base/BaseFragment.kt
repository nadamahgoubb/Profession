package com.horizons.profession.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
 import com.horizons.profession.util.ext.bindView
import com.horizons.profession.util.ext.castToActivity
import com.horizons.profession.util.ext.showAppToast
import com.horizons.profession.util.ext.showErrorDialog


abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    open fun mPageTitle(): String? = null

    /**
     * first: background res
     * second: height
     * */
    open fun mPageToolbarBackground(): Pair<Int, Int>? = null
     open val mTitleGravity: Int = Gravity.CENTER_VERTICAL
    open val showBottomNavigationView: Boolean = true

    abstract fun onFragmentReady()


    private var _binding: B? = null
    open lateinit var binding: B
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView()
        binding = _binding!!
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //   setToolbarTitle(mPageTitle())
        onFragmentReady()
    }


    fun showProgress(show: Boolean = true) {
        castToActivity<BaseActivity<*>> {
            it?.baseShowProgress?.set(show)
        }
    }
    fun showDialog(message: String?) {
        context?.showErrorDialog(message)
    }

    fun showToast(message: String?) {
        context?.showAppToast(message)
    }

    override fun onDestroyView() {
        showProgress(false)
         _binding = null
        super.onDestroyView()
    }


}
