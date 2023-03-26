package com.rasyidin.moneyverse.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.domain.model.home.HomeUi
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase): ViewModel() {

    private var _uiState = mutableStateOf(HomeUi())
    val uiState: State<HomeUi> get() = _uiState

    fun getTotalSaldo() {
        viewModelScope.launch {
            useCase.getTotalSaldo().collect { result ->
                result.onSuccess {  saldo ->
                    _uiState.value = uiState.value.copy(
                        totalSaldo = saldo ?: 0
                    )
                }

                result.onFailure { _, message ->
                    _uiState.value = uiState.value.copy(
                        errorMessage = message
                    )
                }
            }
        }
    }

    fun getRecentTransactions() {
        viewModelScope.launch {
            useCase.getRecentFiveTransactions().collect { result ->
                result.onSuccess {  transactions ->
                    transactions?.let {
                        _uiState.value = uiState.value.copy(
                            recentTransactions = transactions
                        )
                    }
                }
            }
        }
    }
}