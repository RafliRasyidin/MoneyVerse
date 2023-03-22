package com.rasyidin.moneyverse.ui.screen.transaction.add

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentAddTransactionBinding
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.hideBotNav


class AddTransactionFragment :
    FragmentBinding<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private val pagerAdapter by lazy { TransactionPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle) }
    private lateinit var mediator: TabLayoutMediator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        setupViewPager()
    }

    private fun setupToolbar() {
        binding.toolbarCompose.setContent {
            MoneyVerseTheme() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    MVToolbar(
                        title = stringResource(id = R.string.tambah_transaksi),
                        onBackClick = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.vpTransaction.adapter = pagerAdapter

        binding.vpTransaction.isUserInputEnabled = false
        mediator = TabLayoutMediator(binding.tabs, binding.vpTransaction) { tabs, position ->
            tabs.text = when (position) {
                0 -> getString(R.string.pengeluaran)
                1 -> getString(R.string.pemasukan)
                else -> getString(R.string.transfer)
            }
        }
        mediator.attach()
    }

    override fun onResume() {
        super.onResume()
        hideBotNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator.detach()
    }
}