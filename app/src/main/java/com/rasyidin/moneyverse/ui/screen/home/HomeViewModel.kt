package com.rasyidin.moneyverse.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.idleStateFlow
import com.rasyidin.moneyverse.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase): ViewModel() {

    private var _totalSaldoState: MutableStateFlow<ResultState<Long>> = idleStateFlow()
    val totalSaldo get() = _totalSaldoState.asStateFlow()

    fun getTotalSaldo() {
        viewModelScope.launch {
            useCase.getTotalSaldo().collect { resultState ->
                _totalSaldoState.value = resultState
            }
        }
    }
}