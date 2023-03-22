package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome

import android.os.Bundle
import android.view.View
import com.rasyidin.moneyverse.databinding.FragmentOutcomeBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

class OutcomeFragment : FragmentBinding<FragmentOutcomeBinding>(FragmentOutcomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()
    }

    private fun setContent() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                OutcomeScreen()
            }
        }
    }

}