package com.rasyidin.moneyverse.ui.screen.anggaran.add

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentAddAnggaranBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.hideBotNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAnggaranFragment : FragmentBinding<FragmentAddAnggaranBinding>(FragmentAddAnggaranBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()
    }

    private fun setContent() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                AddAnggaranScreen(navController = findNavController())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBotNav()
    }


}