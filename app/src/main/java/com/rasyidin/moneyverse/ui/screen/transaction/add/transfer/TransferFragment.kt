package com.rasyidin.moneyverse.ui.screen.transaction.add.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.databinding.FragmentTransferBinding
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : FragmentBinding<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    private val viewModel: TransferViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContent()

        observeUpsertTransaction()
    }

    private fun setContent() {
        binding.composeView.setContent {
            MoneyVerseTheme {
                TransferScreen()
            }
        }
    }

    private fun observeUpsertTransaction () {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.upsertState.collect { result ->
                result.onSuccess {
                    findNavController().popBackStack()
                }

                result.onFailure { _, message ->
                    showShortToast(message ?: "Gagal menambah transaksi")
                }
            }
        }
    }

}