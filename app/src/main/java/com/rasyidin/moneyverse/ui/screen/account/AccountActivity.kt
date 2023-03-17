package com.rasyidin.moneyverse.ui.screen.account

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rasyidin.moneyverse.databinding.ActivityAccountBinding
import com.rasyidin.moneyverse.ui.component.ActivityBinding

class AccountActivity : ActivityBinding<ActivityAccountBinding>(ActivityAccountBinding::inflate) {

    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHost =
            supportFragmentManager.findFragmentById(binding.fragmentContainerAccount.id) as NavHostFragment
        navController = navHost.navController
    }

}