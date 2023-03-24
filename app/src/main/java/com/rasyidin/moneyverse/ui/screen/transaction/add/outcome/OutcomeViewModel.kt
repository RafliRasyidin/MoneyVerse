package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome


import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.MoneyVerseApp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.*
import com.rasyidin.moneyverse.domain.model.transaction.OutcomeUi
import com.rasyidin.moneyverse.domain.model.transaction.Transaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType
import com.rasyidin.moneyverse.domain.usecase.transaction.outcome.OutcomeUseCase
import com.rasyidin.moneyverse.ui.theme.ColorBgBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutcomeViewModel @Inject constructor(private val useCase: OutcomeUseCase) : ViewModel() {

    private var _uiState = mutableStateOf(OutcomeUi())
    val uiState: State<OutcomeUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    var buttonState by mutableStateOf(false)

    private var _sheetState: MutableState<SheetOutcomeTransactionEvent> = mutableStateOf(SheetOutcomeTransactionEvent.Idle)
    var sheetState: State<SheetOutcomeTransactionEvent> = _sheetState

    init {
        getListAccounts()
        getCategories()
    }

    fun getListAccounts() {
        viewModelScope.launch {
            useCase.getListAccount().collect { result ->
                result.onSuccess { accounts ->
                    accounts?.let {
                        val account = accounts.first()
                        _uiState.value = uiState.value.copy(
                            accounts = accounts,
                            accountId = account.id,
                            accountName = account.name,
                            accountBgColor = account.bgColor,
                            accountIconPath = account.iconPath
                        )
                    }
                }

                result.onEmpty { message ->
                    _uiState.value = uiState.value.copy(
                        accounts = emptyList()
                    )
                }

                result.onFailure { throwable, message ->
                    _uiState.value = uiState.value.copy(
                        errorMessage = message
                    )
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            useCase.getOutcomeCategories().collect { result ->
                result.onSuccess { categories ->
                    categories?.let {
                        val category = categories.first()
                        _uiState.value = uiState.value.copy(
                            categories = categories,
                            categoryId = -1,
                            categoryName = MoneyVerseApp.appContext!!.getString(R.string.pilih_kategori),
                            categoryBgColor = ColorBgBlue.toArgb(),
                            categoryIconPath = R.drawable.ic_tagihan
                        )
                    }
                }

                result.onEmpty { _ ->
                    _uiState.value = uiState.value.copy(
                        categories = emptyList()
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

    fun onEvent(event: OutcomeTransactionEvent) {
        when (event) {
            is OutcomeTransactionEvent.OnNominalChange -> {
                _uiState.value = uiState.value.copy(nominal = event.text.toLongOrNull() ?: 0)
                setButtonValidation()
            }
            is OutcomeTransactionEvent.OnNotesChange -> _uiState.value = uiState.value.copy(notes = event.text)
            is OutcomeTransactionEvent.OnSelectAccount -> {
                _uiState.value = uiState.value.copy(
                    accountId = event.id,
                    accountIconPath = event.iconPath,
                    accountBgColor = event.bgColor,
                    accountName = event.name
                )
                setButtonValidation()
            }
            is OutcomeTransactionEvent.OnSelectCategory -> {
                _uiState.value = uiState.value.copy(
                    categoryId = event.id,
                    categoryIconPath = event.iconPath,
                    categoryBgColor = event.bgColor,
                    categoryName = event.name
                )
                setButtonValidation()
            }
            is OutcomeTransactionEvent.OnSelectDate -> {
                _uiState.value = uiState.value.copy(
                    date = event.text
                )
                setButtonValidation()
            }
            is OutcomeTransactionEvent.SaveTransaction -> upsertTransaction()
            is OutcomeTransactionEvent.ShowSheetAccounts -> _sheetState.value = SheetOutcomeTransactionEvent.ShowSheetAccounts
            is OutcomeTransactionEvent.ShowSheetCategories -> _sheetState.value = SheetOutcomeTransactionEvent.ShowSheetCategories
            is OutcomeTransactionEvent.ShowSheetCalendar -> _sheetState.value = SheetOutcomeTransactionEvent.ShowSheetCalendar
            is OutcomeTransactionEvent.HideSheet -> _sheetState.value = SheetOutcomeTransactionEvent.Idle
        }
    }

    private fun upsertTransaction() {
        viewModelScope.launch {
            useCase.addTransaction(
                Transaction(
                    nominal = uiState.value.nominal,
                    createdAt = uiState.value.date,
                    notes = uiState.value.notes,
                    transactionType = TransactionType.OUTCOME,
                    categoryId = uiState.value.categoryId,
                    sourceAccountId = uiState.value.accountId
                )
            ).collect { result ->
                _upsertState.send(result)
            }
        }
    }

    private fun setButtonValidation() {
        val nominal = uiState.value.nominal
        val isNominalNotEmpty = nominal != 0L && nominal.toString().isNotEmpty()
        val isCategorySelected = uiState.value.categoryId != -1
        val isAccountSelected = uiState.value.accountId != -1
        val isDateSelected = uiState.value.date.isNotEmpty()
        buttonState = isNominalNotEmpty && isCategorySelected && isAccountSelected && isDateSelected
    }
}