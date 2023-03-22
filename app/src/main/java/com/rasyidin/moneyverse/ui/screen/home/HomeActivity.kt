package com.rasyidin.moneyverse.ui.screen.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.ActivityHomeBinding
import com.rasyidin.moneyverse.ui.component.ActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ActivityBinding<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()

        onViewClick()
    }

    private fun onViewClick() {
        binding.fabAdd.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_up)
                .setExitAnim(R.anim.slide_down)
                .setPopEnterAnim(R.anim.slide_up)
                .setPopExitAnim(R.anim.slide_down)
                .build()

            navController.navigate(R.id.addTransactionFragment, null, navOptions)
        }
    }

    private fun setupNavigation() {
        navHost =
            supportFragmentManager.findFragmentById(binding.fragmentContainerHome.id) as NavHostFragment
        navController = navHost.navController
        binding.botNav.setupWithNavController(navController)
    }
}