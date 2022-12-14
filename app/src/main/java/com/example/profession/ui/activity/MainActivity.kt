package com.example.profession.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.profession.databinding.ActivityMainBinding
import com.example.profession.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import com.example.profession.R


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
     var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController()
    }
    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
        //bottom nav
       binding.navViewBottom.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.orderFragment),
            binding.drawerLayout
        )
        //menu item click handle
        binding.navViewSideNav.setupWithNavController(navController)




        //drawer
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle?.syncState()

        // to make the Navigation drawer icon always appear on the action bar

        // to make the Navigation drawer icon always appear on the action bar

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.orderFragment),
            binding.drawerLayout
        )
        //menu item click handle
        binding.navViewSideNav.setupWithNavController(navController)

        actionBarDrawerToggle?.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);  //
  //   setupActionBarWithNavController(navController, appBarConfiguration)
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

    //open drawer when drawer icon clicked and back btn presse
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
    fun openDrawer(){
        binding.drawerLayout.openDrawer(GravityCompat.END)
    }
    fun closeDrawer(){
        binding.drawerLayout.closeDrawer(GravityCompat.END)

    }
}
