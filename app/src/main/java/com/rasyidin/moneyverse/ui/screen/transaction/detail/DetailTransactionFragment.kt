package com.rasyidin.moneyverse.ui.screen.transaction.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentDetailTransactionBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.hideBotNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTransactionFragment : FragmentBinding<FragmentDetailTransactionBinding>(FragmentDetailTransactionBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                DetailTransactionScreen(navController = findNavController())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBotNav()
    }
}