package com.rasyidin.moneyverse.ui.screen.transaction.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentDetailTransactionBinding
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.hideBotNav
import com.rasyidin.moneyverse.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailTransactionFragment : FragmentBinding<FragmentDetailTransactionBinding>(FragmentDetailTransactionBinding::inflate) {

    private val viewModel: DetailTransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()

        observeDeleteTransaction()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                DetailTransactionScreen(navController = findNavController())
            }
        }
    }

    private fun observeDeleteTransaction() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deleteState.collect { result ->
                result.onSuccess {
                    showShortToast(getString(R.string.transaksi_berhasil_dihapus))
                    findNavController().popBackStack()
                }

                result.onFailure { _, message ->
                    showShortToast(getString(R.string.transaksi_gagal_dihapus))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDetailTransaction()
        hideBotNav()
    }
}