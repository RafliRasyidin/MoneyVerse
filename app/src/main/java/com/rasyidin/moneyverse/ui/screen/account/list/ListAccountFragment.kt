package com.rasyidin.moneyverse.ui.screen.account.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentListAccountBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAccountFragment :
    FragmentBinding<FragmentListAccountBinding>(FragmentListAccountBinding::inflate) {

    private val viewModel by viewModels<ListAccountViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTotalSaldo()
        viewModel.getAccounts()
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
                        findNavController().navigate(ListAccountFragmentDirections.actionListAccountFragmentToDetailAccountFragment(account.id))
                    }
                )
            }
        }
    }
}