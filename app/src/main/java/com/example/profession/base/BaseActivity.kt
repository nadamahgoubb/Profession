package com.example.profession.base

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.profession.data.PrefsHelper
import com.example.profession.util.LanguageUtils
import com.example.profession.util.ext.bindView
import java.util.*


abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {


    override fun attachBaseContext(newBase: Context) {
        // get chosen language from shread preference
        val locale = Locale("ar")
        val localeUpdatedContext: ContextWrapper = LanguageUtils.updateLocale(newBase, locale)
        super.attachBaseContext(localeUpdatedContext)


    }


    fun showLoading(v:View) {
     v.visibility = View.VISIBLE
    }

    fun hideLoading(v:View) {
       v.visibility = View.GONE
    }
    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = bindView()
    }

    public override fun onResume() {
        super.onResume()
    }

    lateinit var binding: B

    fun setOpacityBackground(view: View, @ColorRes color: Int) {
        view.setBackgroundColor(
            try {
                ContextCompat.getColor(this, color)
            } catch (e: Resources.NotFoundException) {
                0
            }
        )
    }


}