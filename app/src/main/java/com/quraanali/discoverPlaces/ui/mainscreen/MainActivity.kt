package com.quraanali.discoverPlaces.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.quraanali.discoverPlaces.R
import com.quraanali.discoverPlaces.databinding.ActivityMainBinding
import com.quraanali.discoverPlaces.ui.AppListener
import com.quraanali.discoverPlaces.utils.LocalizationHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AppListener,
    NavigationView.OnNavigationItemSelectedListener {


    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalizationHelper.onAttach(newBase))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        navigateToHome()

        appBarConfiguration =
            AppBarConfiguration.Builder(setOf()).setOpenableLayout(viewBinding.drawerLayout).build()
        viewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
        viewBinding.navView.let {
            NavigationUI.setupWithNavController(
                it, navHostFragment.navController
            )
        }

    }

    private fun navigateToHome(bundle: Bundle? = null) {
        val graph = creteGraph()
        graph.setStartDestination(R.id.homeFragment)
        navHostFragment.navController.setGraph(graph, bundle)
    }


    private fun creteGraph(): NavGraph {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        return inflater.inflate(R.navigation.init_graph)
    }


    override fun showProgress() {
        viewBinding.layoutProgress.root.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewBinding.layoutProgress.root.visibility = View.GONE
    }

    override fun initNavDrawer(toolbar: Toolbar?) {
        window?.decorView?.post {
            if (!this::viewBinding.isInitialized) {
                return@post
            }

            toolbar?.let {
                supportActionBar?.setDisplayShowTitleEnabled(false)
                NavigationUI.setupWithNavController(
                    it, navHostFragment.navController, appBarConfiguration
                )
                viewBinding.navView.setNavigationItemSelectedListener(this)
                viewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)


            } ?: kotlin.run {
                viewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
            }
        }
    }

    override fun initPushNotification() {
        TODO("Not yet implemented")
    }

    override fun changeLocal(bundle: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewBinding.drawerLayout.closeDrawers()

        return NavigationUI.onNavDestinationSelected(item, navHostFragment.navController)
    }

}