package com.example.profession.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.laundrydelivery.util.ext.isNull
import com.example.profession.base.BaseActivity
import com.example.profession.databinding.ActivityMainBinding
import com.example.profession.util.Constants
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header.view.*
import  com.example.profession.R
import com.example.profession.data.dataSource.repoistry.PrefsHelper
import com.example.profession.util.ext.loadImage
import com.example.profession.util.ext.showActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {


    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController()
        binding.progress = baseShowProgress

    }


    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //bottom nav
        binding.navViewBottom.setupWithNavController(navController)

        binding.navViewSideNav.setupWithNavController(navController)

        //actionBarDrawerToggle =
        ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)


        val toggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        var menu =  binding.navViewSideNav.menu
        var headerview = binding.navViewSideNav.getHeaderView(0);
        if(PrefsHelper.getUserData().isNull()){
            headerview.iv_user.isVisible= true
            headerview.tv_name.isVisible= false
menu.findItem(R.id.logout).isVisible= false
menu.findItem(R.id.login).isVisible= true
        }else {
            headerview.tv_name.setText(PrefsHelper.getUserData()?.name)
            headerview.iv_user.loadImage(PrefsHelper.getUserData()?.photo, isCircular = true)

            menu.findItem(R.id.logout).isVisible= true
            menu.findItem(R.id.login).isVisible= false
        }
        headerview.iv_cancel.setOnClickListener {
            closeDrawer()

        }
        binding.navViewSideNav.setNavigationItemSelectedListener(this)

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var fragment: Fragment? = null
        val fragmentManager: FragmentManager = supportFragmentManager
        if (id == R.id.logout) {
            PrefsHelper.clear()
            var intent = Intent(this, AuthActivity::class.java)
            intent.putExtra(Constants.Start, Constants.login)
            startActivity(intent)
            this?.finish()
        } else if (id == R.id.settingsFragment) {

            navController.navigate(R.id.settingsFragment)
        } else if (id == R.id.rightsAndTermsFragment) {
            navController.navigate(R.id.rightsAndTermsFragment)


        } else if (id == R.id.customerServiceFragment) {
            navController.navigate(R.id.customerServiceFragment)

        } else if (id == R.id.contactUsFragment) {
            navController.navigate(R.id.contactUsFragment)

        }else if (id == R.id.login) {
startActivity(Intent(this@MainActivity, AuthActivity::class.java))
           this.finish()
         }
        closeDrawer()

        return true
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    //open drawer when drawer icon clicked and back btn presse
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    fun openDrawer() {
        //    if(!binding.drawerLayout.isVisible)
        //    binding.drawerLayout.openDrawer(GravityCompat.END)
        binding.drawerLayout.openDrawer(Gravity.LEFT);

    }

    fun closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    fun showBottomNav(isVisible: Boolean) {
        binding.navViewBottom.isVisible = isVisible
    }

    fun showSideNav(isVisible: Boolean) {
        binding.navViewSideNav.isVisible = isVisible
    }

}
