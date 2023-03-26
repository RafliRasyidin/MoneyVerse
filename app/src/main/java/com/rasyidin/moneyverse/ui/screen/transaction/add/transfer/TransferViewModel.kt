package com.rasyidin.moneyverse.ui.screen.transaction.add.transfer

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.MoneyVerseApp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.*
import com.rasyidin.moneyverse.domain.model.transaction.Transaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType
import com.rasyidin.moneyverse.domain.model.transaction.TransferUi
import com.rasyidin.moneyverse.domain.usecase.transaction.tramsfer.TransferUseCase
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val useCase: TransferUseCase): ViewModel() {

    private var _uiState = mutableStateOf(TransferUi())
    val uiState: State<TransferUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    var buttonState by mutableStateOf(false)

    private var _sheetState: MutableState<SheetTransferEvent> = mutableStateOf(SheetTransferEvent.Idle)
    var sheetState: State<SheetTransferEvent> = _sheetState

    init {
        getListAccounts()
    }

    fun onEvent(event: TransferEvent) {
        when (event) {
            is TransferEvent.OnNominalChange -> {
                _uiState.value = uiState.value.copy(nominal = event.nominal.toLongOrNull() ?: 0)
                setButtonValidation()
            }
            is TransferEvent.OnNotesChange -> _uiState.value = uiState.value.copy(notes = event.text)
            is TransferEvent.OnSelectDate -> {
                _uiState.value = uiState.value.copy(
                    date = event.text
                )
                setButtonValidation()
            }
            is TransferEvent.OnSelectFromAccount -> {
                _uiState.value = uiState.value.copy(
                    fromAccountId = event.id,
                    fromAccountBgColor = event.bgColor,
                    fromAccountIconPath = event.iconPath,
                    fromAccountName = event.name
                )
                setButtonValidation()
            }
            is TransferEvent.OnSelectToAccount -> {
                _uiState.value = uiState.value.copy(
                    toAccountId = event.id,
                    toAccountBgColor = event.bgColor,
                    toAccountIconPath = event.iconPath,
                    toAccountName = event.name
                )
                setButtonValidation()
            }
            is TransferEvent.Save -> upsertTransaction()
            is TransferEvent.ShowSheetCalendar -> _sheetState.value = SheetTransferEvent.ShowSheetCalendar
            is TransferEvent.ShowSheetFromAccount -> _sheetState.value = SheetTransferEvent.ShowSheetFromAccount
            is TransferEvent.ShowSheetToAccount -> _sheetState.value = SheetTransferEvent.ShowSheetToAccount
            is TransferEvent.HideSheet -> _sheetState.value = SheetTransferEvent.Idle
        }
    }

    private fun getListAccounts() {
        viewModelScope.launch {
            useCase.getListAccount().collect { result ->
                result.onSuccess { accounts ->
                    accounts?.let {
                        val firstAccount = accounts.first()
                        val isHadTwoOrMoreAccounts = accounts.size > 1
                        _uiState.value = uiState.value.copy(
                            accounts = accounts,
                            fromAccountId = firstAccount.id,
                            fromAccountName = firstAccount.name,
                            fromAccountBgColor = firstAccount.bgColor,
                            fromAccountIconPath = firstAccount.iconPath,
                        )
                        if (isHadTwoOrMoreAccounts) {
                            val secondAccount = accounts[1]
                            _uiState.value = uiState.value.copy(
                                toAccountId = secondAccount.id,
                                toAccountBgColor = secondAccount.bgColor,
                                toAccountIconPath = secondAccount.iconPath,
                                toAccountName = secondAccount.name
                            )
                        } else {
                            _uiState.value = uiState.value.copy(
                                toAccountId = -1,
                                toAccountBgColor = ColorBgPurple.toArgb(),
                                toAccountIconPath = R.drawable.ic_tagihan,
                                toAccountName = MoneyVerseApp.appContext!!.getString(R.string.pilih_akun)
                            )
                        }
                    }
                }

                result.onEmpty {
                    _uiState.value = uiState.value.copy(
                        accounts = emptyList()
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

    private fun upsertTransaction() {
        viewModelScope.launch {
            useCase.addTransaction(
                Transaction(
                    nominal = uiState.value.nominal,
                    createdAt = uiState.value.date,
                    notes = uiState.value.notes,
                    transactionType = TransactionType.TRANSFER,
                    fromAccountId = uiState.value.fromAccountId,
                    toAccountId = uiState.value.toAccountId,
                )
            ).collect { result ->
                _upsertState.send(result)
            }
        }
    }

    private fun setButtonValidation() {
        val nominal = uiState.value.nominal
        val isNominalNotEmpty = nominal != 0L && nominal.toString().isNotEmpty()
        val isFromAccountIdSelected = uiState.value.fromAccountId != -1
        val isToAccountSelected = uiState.value.toAccountId != -1
        val isDateSelected = uiState.value.date.isNotEmpty()
        buttonState = isNominalNotEmpty && isFromAccountIdSelected && isToAccountSelected && isDateSelected
    }
}