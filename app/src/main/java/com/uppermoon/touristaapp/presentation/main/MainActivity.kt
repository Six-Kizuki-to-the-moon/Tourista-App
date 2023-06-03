package com.uppermoon.touristaapp.presentation.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.databinding.ActivityMainBinding
import com.uppermoon.touristaapp.presentation.dashboard.DashboardFragment
import com.uppermoon.touristaapp.presentation.home.HomeFragment
import com.uppermoon.touristaapp.presentation.notifications.NotificationsFragment
import com.uppermoon.touristaapp.presentation.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        replaceFragment(HomeFragment())
//
//                val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//
//        binding.navView.setOnClickListener {
//            when(it.id){
//                R.id.navigation_home -> replaceFragment(HomeFragment())
//                R.id.navigation_dashboard -> replaceFragment(DashboardFragment())
//                R.id.navigation_notifications -> replaceFragment(NotificationsFragment())
//                R.id.navigation_profile -> replaceFragment(ProfileFragment())
//                else ->{
//
//                }
//            }
//            true
//        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

//    private fun replaceFragment(fragment: Fragment){
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment)
//        fragmentTransaction.commit()
//    }
}