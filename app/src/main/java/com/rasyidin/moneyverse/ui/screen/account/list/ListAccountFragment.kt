package com.rasyidin.moneyverse.ui.screen.account.list

import android.os.Bundle
import android.view.View
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentListAccountBinding
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

class ListAccountFragment : FragmentBinding<FragmentListAccountBinding>(FragmentListAccountBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            val accounts = mutableListOf<Account>()
            for (i in 0 until 11) {
                accounts.add(
                    Account(
                        id = i,
                        nominal = 100000,
                        name = "Cash",
                        updatedAt = "",
                        iconPath = R.drawable.ic_cash,
                        bgColor = ColorBgGreen.toArgb()
                    )
                )
            }
            MoneyVerseTheme() {
                ListAccountScreen(
                    totalSaldo = 900000,
                    accounts = accounts,
                    onBackClick = { requireActivity().finish() },
                    onAddAccountClick = {
                        findNavController().navigate(ListAccountFragmentDirections.actionListAccountFragmentToDetailAccountFragment())
                    },
                    onItemAccountClick = { account ->

                    }
                )
            }
        }
    }
}