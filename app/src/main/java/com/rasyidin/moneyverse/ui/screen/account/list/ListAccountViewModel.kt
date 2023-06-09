package com.rasyidin.moneyverse.ui.screen.account.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.domain.model.account.AccountUi
import com.rasyidin.moneyverse.domain.onEmpty
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.domain.usecase.account.AccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAccountViewModel @Inject constructor(private val useCase: AccountUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(AccountUi())
    val uiState: State<AccountUi> = _uiState

    fun getTotalSaldo() {
        viewModelScope.launch {
            useCase.getTotalSaldo().collect { result ->
                result.onSuccess {  saldo ->
                    _uiState.value = uiState.value.copy(totalSaldo = saldo ?: 0)
                }

                result.onFailure { throwable, message ->
                    _uiState.value = uiState.value.copy(totalSaldo = 0, errorMessage = message.toString())
                }
            }
        }
    }

    fun getAccounts() {
        viewModelScope.launch {
            useCase.getListAccount().collect { result ->
                result.onSuccess { accounts ->
                    accounts?.let {
                        _uiState.value = uiState.value.copy(accounts = accounts)
                    }
                }

                result.onEmpty { message ->
                    _uiState.value = uiState.value.copy(accounts = emptyList())
                }

                result.onFailure { throwable, message ->
                    _uiState.value = uiState.value.copy(accounts = emptyList(), errorMessage = message.toString())
                }
            }
        }
    }
}