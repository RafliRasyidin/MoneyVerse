package com.rasyidin.moneyverse.ui.screen.account.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentListAccountBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAccountFragment :
    FragmentBinding<FragmentListAccountBinding>(FragmentListAccountBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }


    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme() {
                ListAccountScreen(
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