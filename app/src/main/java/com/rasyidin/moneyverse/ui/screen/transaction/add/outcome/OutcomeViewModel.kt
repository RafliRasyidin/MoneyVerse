package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome


import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.MoneyVerseApp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.*
import com.rasyidin.moneyverse.domain.model.transaction.OutcomeUi
import com.rasyidin.moneyverse.domain.model.transaction.Transaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType.OUTCOME
import com.rasyidin.moneyverse.domain.usecase.transaction.outcome.OutcomeUseCase
import com.rasyidin.moneyverse.ui.theme.ColorBgBlue
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutcomeViewModel @Inject constructor(
    private val useCase: OutcomeUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = mutableStateOf(OutcomeUi())
    val uiState: State<OutcomeUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    var buttonState by mutableStateOf(false)

    private var _sheetState: MutableState<SheetOutcomeEvent> = mutableStateOf(SheetOutcomeEvent.Idle)
    var sheetState: State<SheetOutcomeEvent> = _sheetState

    private var transactionId: Int = -1

    init {
        getDetailTransaction()
        getListAccounts()
        getCategories()
    }

    private fun getDetailTransaction() {
        savedStateHandle.get<Int>("transactionId")?.let { transactionId ->
            viewModelScope.launch {
                useCase.getDetailTransaction(transactionId).collect { result ->
                    result.onSuccess { detailTransaction ->
                        detailTransaction?.let {
                            this@OutcomeViewModel.transactionId = transactionId
                            if (transactionId != -1) {
                                _uiState.value = uiState.value.copy(
                                    id = detailTransaction.id,
                                    nominal = detailTransaction.nominal,
                                    date = detailTransaction.createdAt,
                                    notes = detailTransaction.notes ?: "",
                                    transactionType = detailTransaction.transactionType,
                                    categoryId = detailTransaction.categoryId ?: -1,
                                    accountId = detailTransaction.fromAccountId,
                                    accountName = detailTransaction.accountName,
                                    accountIconPath = detailTransaction.accountIconPath,
                                    accountBgColor = detailTransaction.accountBgColor,
                                    categoryName = detailTransaction.categoryName ?: "",
                                    categoryIconPath = detailTransaction.categoryIconPath
                                        ?: R.drawable.ic_tagihan,
                                    categoryBgColor = detailTransaction.categoryBgColor
                                        ?: ColorBgPurple.toArgb(),
                                    editedAccountId = detailTransaction.fromAccountId
                                )
                                setButtonValidation()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getListAccounts() {
        viewModelScope.launch {
            useCase.getListAccount().collect { result ->
                result.onSuccess { accounts ->
                    accounts?.let {
                        if (transactionId == -1) {
                            val account = accounts.first()
                            _uiState.value = uiState.value.copy(
                                accounts = accounts,
                                accountId = account.id,
                                accountName = account.name,
                                accountBgColor = account.bgColor,
                                accountIconPath = account.iconPath
                            )
                        } else {
                            _uiState.value = uiState.value.copy(
                                accounts = accounts,
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

    private fun getCategories() {
        viewModelScope.launch {
            useCase.getOutcomeCategories().collect { result ->
                result.onSuccess { categories ->
                    categories?.let {
                        if (transactionId == -1) {
                            _uiState.value = uiState.value.copy(
                                categories = categories,
                                categoryId = -1,
                                categoryName = MoneyVerseApp.appContext!!.getString(R.string.pilih_kategori),
                                categoryBgColor = ColorBgBlue.toArgb(),
                                categoryIconPath = R.drawable.ic_tagihan
                            )
                        } else {
                            _uiState.value = uiState.value.copy(
                                categories = categories,
                            )
                        }
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

    fun onEvent(event: OutcomeEvent) {
        when (event) {
            is OutcomeEvent.OnNominalChange -> {
                _uiState.value = uiState.value.copy(nominal = event.text.toLongOrNull() ?: 0)
                setButtonValidation()
            }
            is OutcomeEvent.OnNotesChange -> _uiState.value = uiState.value.copy(notes = event.text)
            is OutcomeEvent.OnSelectAccount -> {
                _uiState.value = uiState.value.copy(
                    accountId = event.id,
                    accountIconPath = event.iconPath,
                    accountBgColor = event.bgColor,
                    accountName = event.name
                )
                setButtonValidation()
            }
            is OutcomeEvent.OnSelectCategory -> {
                _uiState.value = uiState.value.copy(
                    categoryId = event.id,
                    categoryIconPath = event.iconPath,
                    categoryBgColor = event.bgColor,
                    categoryName = event.name
                )
                setButtonValidation()
            }
            is OutcomeEvent.OnSelectDate -> {
                _uiState.value = uiState.value.copy(
                    date = event.text
                )
                setButtonValidation()
            }
            is OutcomeEvent.Save -> upsertTransaction()
            is OutcomeEvent.ShowSheetAccounts -> _sheetState.value = SheetOutcomeEvent.ShowSheetAccounts
            is OutcomeEvent.ShowSheetCategories -> _sheetState.value = SheetOutcomeEvent.ShowSheetCategories
            is OutcomeEvent.ShowSheetCalendar -> _sheetState.value = SheetOutcomeEvent.ShowSheetCalendar
            is OutcomeEvent.HideSheet -> _sheetState.value = SheetOutcomeEvent.Idle
        }
    }

    private fun upsertTransaction() {
        viewModelScope.launch {
            if (transactionId == -1) {
                useCase.addTransaction(
                    Transaction(
                        nominal = uiState.value.nominal,
                        createdAt = uiState.value.date,
                        notes = uiState.value.notes,
                        transactionType = OUTCOME,
                        categoryId = uiState.value.categoryId,
                        fromAccountId = uiState.value.accountId
                    )
                ).collect { result ->
                    _upsertState.send(result)
                }
            } else {
                val editedAccountId =
                    if (uiState.value.editedAccountId == uiState.value.accountId) -1
                    else uiState.value.editedAccountId

                useCase.addTransaction(
                    Transaction(
                        id = transactionId,
                        nominal = uiState.value.nominal,
                        createdAt = uiState.value.date,
                        notes = uiState.value.notes,
                        transactionType = OUTCOME,
                        categoryId = uiState.value.categoryId,
                        fromAccountId = uiState.value.accountId
                    ),
                    editedAccountId
                ).collect { result ->
                    _upsertState.send(result)
                }
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