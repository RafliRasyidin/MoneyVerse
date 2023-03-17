package com.rasyidin.moneyverse.ui.screen.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.ActivityHomeBinding
import com.rasyidin.moneyverse.ui.component.ActivityBinding

class HomeActivity : ActivityBinding<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        navHost =
            supportFragmentManager.findFragmentById(binding.fragmentContainerHome.id) as NavHostFragment
        navController = navHost.navController
        binding.botNav.setupWithNavController(navController)
    }
}