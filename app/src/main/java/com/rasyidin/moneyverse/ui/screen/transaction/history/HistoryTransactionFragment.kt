package com.rasyidin.moneyverse.ui.screen.transaction.history

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentHistoryTransactionBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.hideBotNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryTransactionFragment :
    FragmentBinding<FragmentHistoryTransactionBinding>(FragmentHistoryTransactionBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupContent()
    }

    private fun setupContent() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                HistoryTransactionScreen(
                    navController = findNavController(),
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBotNav()
    }

}