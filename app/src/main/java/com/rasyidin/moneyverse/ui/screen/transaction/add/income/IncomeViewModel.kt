package com.rasyidin.moneyverse.ui.screen.transaction.add.income

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.MoneyVerseApp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.*
import com.rasyidin.moneyverse.domain.model.transaction.IncomeUi
import com.rasyidin.moneyverse.domain.model.transaction.Transaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType
import com.rasyidin.moneyverse.domain.usecase.transaction.income.IncomeUseCase
import com.rasyidin.moneyverse.ui.theme.ColorBgBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(private val useCase: IncomeUseCase): ViewModel() {

    private var _uiState = mutableStateOf(IncomeUi())
    val uiState: State<IncomeUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    var buttonState by mutableStateOf(false)

    private var _sheetState: MutableState<SheetIncomeEvent> = mutableStateOf(SheetIncomeEvent.Idle)
    val sheetState: State<SheetIncomeEvent> = _sheetState

    init {
        getListAccounts()
        getCategories()
    }

    fun onEvent(event: IncomeEvent) {
        when (event) {
            is IncomeEvent.OnNominalChange -> {
                _uiState.value = uiState.value.copy(nominal = event.nominal.toLongOrNull() ?: 0)
                setButtonValidation()
            }
            is IncomeEvent.OnNotesChange -> _uiState.value = uiState.value.copy(notes = event.text)
            is IncomeEvent.OnSelectAccount -> {
                _uiState.value = uiState.value.copy(
                    accountId = event.id,
                    accountIconPath = event.iconPath,
                    accountBgColor = event.bgColor,
                    accountName = event.name
                )
                setButtonValidation()
            }
            is IncomeEvent.OnSelectCategory -> {
                _uiState.value = uiState.value.copy(
                    categoryId = event.id,
                    categoryIconPath = event.iconPath,
                    categoryBgColor = event.bgColor,
                    categoryName = event.name
                )
                setButtonValidation()
            }
            is IncomeEvent.OnSelectDate -> {
                _uiState.value = uiState.value.copy(
                    date = event.text
                )
                setButtonValidation()
            }
            is IncomeEvent.Save -> upsertTransaction()
            is IncomeEvent.ShowSheetAccounts -> _sheetState.value = SheetIncomeEvent.ShowSheetAccounts
            is IncomeEvent.ShowSheetCalendar -> _sheetState.value = SheetIncomeEvent.ShowSheetCalendar
            is IncomeEvent.ShowSheetCategories -> _sheetState.value = SheetIncomeEvent.ShowSheetCategories
            is IncomeEvent.HideSheet -> _sheetState.value = SheetIncomeEvent.Idle
        }
    }

    private fun getListAccounts() {
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

    private fun getCategories() {
        viewModelScope.launch {
            useCase.getIncomeCategories().collect { result ->
                result.onSuccess { categories ->
                    categories?.let {
                        _uiState.value = uiState.value.copy(
                            categories = categories,
                            categoryId = -1,
                            categoryName = MoneyVerseApp.appContext!!.getString(R.string.pilih_kategori),
                            categoryBgColor = ColorBgBlue.toArgb(),
                            categoryIconPath = R.drawable.ic_tagihan
                        )
                    }
                }

                result.onEmpty {
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

    private fun upsertTransaction() {
        viewModelScope.launch {
            useCase.addTransaction(
                Transaction(
                    nominal = uiState.value.nominal,
                    createdAt = uiState.value.date,
                    notes = uiState.value.notes,
                    transactionType = TransactionType.INCOME,
                    categoryId = uiState.value.categoryId,
                    fromAccountId = uiState.value.accountId
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