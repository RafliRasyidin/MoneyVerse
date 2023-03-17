package com.rasyidin.moneyverse.ui.screen.account.detail

import android.os.Bundle
import android.view.View
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentDetailAccountBinding
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

class DetailAccountFragment :
    FragmentBinding<FragmentDetailAccountBinding>(FragmentDetailAccountBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme() {
                DetailAccountScreen(
                    account = Account(
                        id = 1,
                        nominal = 100000,
                        name = "Cash",
                        updatedAt = "",
                        iconPath = R.drawable.ic_cash,
                        bgColor = ColorBgGreen.toArgb()
                    ),
                    onNameChange = {},
                    onDescChange = {},
                    onNominalChange = {},
                    onBackClick = { findNavController().popBackStack() },
                    onSaveClick = {}
                )
            }
        }
    }

}