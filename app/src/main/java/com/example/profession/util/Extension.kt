package com.example.profession.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.profession.R
import com.google.android.material.snackbar.Snackbar
import java.util.*


object Extension {

    fun Fragment.withFragment(dataBinding: ViewDataBinding) {
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

     fun showSnackBar(message: String,activity:Activity) {
        val snackbar = Snackbar.make(
           activity .findViewById<View>(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }


    fun hideProgressBar(progressBar: ProgressBar) {

        progressBar.visibility = View.INVISIBLE
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }
    fun ImageView.loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.image_gallery))
            .error(R.drawable.error)
            .into(this)
    }


    fun isEmailValid(email: String?): Boolean =
        !email.isNullOrEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    public  fun chat(context: Context, country_code :String,contact :String){
        //contact = PrefsHelper.get // use country code with your phone number
        var  contactt = country_code+contact
        val url = "https://api.whatsapp.com/send?phone=$contactt"
        try {
            val pm: PackageManager = context.getPackageManager()
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(
                context,
                context.getString(R.string.whatsup_not_installed),
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }


}