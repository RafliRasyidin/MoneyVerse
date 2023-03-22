package com.rasyidin.moneyverse.ui.screen.account.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rasyidin.moneyverse.databinding.FragmentDetailAccountBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAccountFragment :
    FragmentBinding<FragmentDetailAccountBinding>(FragmentDetailAccountBinding::inflate) {

    private val args: DetailAccountFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme() {
                DetailAccountScreen(
                    onNameChange = {},
                    onDescChange = {},
                    onNominalChange = {},
                    onBackClick = { findNavController().popBackStack() },
                    onSaveClick = {},
                    onIconSelected = {}
                )
            }
        }
    }

}