package com.rasyidin.moneyverse.ui.screen.home

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentHomeBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.showBotNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : FragmentBinding<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTotalSaldo()
        viewModel.getRecentTransactions()
        showBotNav()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                HomeScreen(
                    modifier = Modifier.padding(top = 24.dp),
                    navController = findNavController()
                )
            }
        }
    }
}