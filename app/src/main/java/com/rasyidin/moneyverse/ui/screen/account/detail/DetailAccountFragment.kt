package com.rasyidin.moneyverse.ui.screen.account.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.databinding.FragmentDetailAccountBinding
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.ui.component.FragmentBinding
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailAccountFragment :
    FragmentBinding<FragmentDetailAccountBinding>(FragmentDetailAccountBinding::inflate) {

    private val viewModel: DetailAccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContentView()

        observeSaveAccount()

        observeDeleteAccount()
    }

    private fun setContentView() {
        binding.composeView.setContent {
            MoneyVerseTheme() {
                DetailAccountScreen(
                    onBackClick = { findNavController().popBackStack() },
                )
            }
        }
    }

    private fun observeSaveAccount() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.upsertState.collect { result ->
                result.onSuccess {
                    findNavController().popBackStack()
                    showShortToast(getString(R.string.akun_berhasil_ditambah))
                }

                result.onFailure { throwable, message ->
                    showShortToast(message.toString())
                }
            }
        }
    }

    private fun observeDeleteAccount() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deleteState.collect { result ->
                result.onSuccess {
                    findNavController().popBackStack()
                    showShortToast(getString(R.string.akun_berhasil_dihapus))
                }

                result.onFailure { throwable, message ->
                    showShortToast(message.toString())
                }
            }
        }
    }

}