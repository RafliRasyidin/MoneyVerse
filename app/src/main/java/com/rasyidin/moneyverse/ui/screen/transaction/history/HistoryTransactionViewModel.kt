package com.rasyidin.moneyverse.ui.screen.transaction.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.history.HistoryTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryTransactionViewModel @Inject constructor(private val useCase: HistoryTransactionUseCase) :
    ViewModel() {

    fun getHistoryTransactions(): Flow<PagingData<ItemTransaction>> =
        useCase.getHistoryTransactions().cachedIn(viewModelScope)
}