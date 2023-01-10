package com.example.profession.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.profession.R

class LoadingUtil(context: Context) : Dialog(context) {

    fun showLoading() {
        show()
    }

    fun hideLoading() {
        dismiss()
        cancel()
    }

    init {
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
        window!!.setBackgroundDrawableResource(R.color.transparent)
//   setLayoutMarginBottom(window!!.requireViewById(R.id.lyt_loading), 300f)



}

fun setLayoutMarginBottom(view: View, bottom: Float) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = bottom.toInt()
    view.layoutParams = layoutParams
}
}