package com.rasyidin.moneyverse.ui.screen.anggaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentAnggaranBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.showBotNav

class AnggaranFragment : FragmentBinding<FragmentAnggaranBinding>(FragmentAnggaranBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()
    }

    private fun setContent() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                AnggaranScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showBotNav()
    }
}