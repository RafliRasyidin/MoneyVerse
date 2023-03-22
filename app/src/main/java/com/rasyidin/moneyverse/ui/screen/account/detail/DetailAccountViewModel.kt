package com.rasyidin.moneyverse.ui.screen.account.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.idleChannel
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.domain.model.account.DetailAccountUi
import com.rasyidin.moneyverse.domain.onFailure
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.domain.usecase.account.AccountUseCase
import com.rasyidin.moneyverse.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAccountViewModel @Inject constructor(
    private val useCase: AccountUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = mutableStateOf(DetailAccountUi())
    val uiState: State<DetailAccountUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    private var currentAccountId: Int? = null

    init {
        getAccountCategories()
        savedStateHandle.get<Int>("accountId")?.let { accountId ->
            if (accountId != -1) {
                viewModelScope.launch {
                    useCase.getDetailAccount(accountId).collect { result ->
                        result.onSuccess { account ->
                            currentAccountId = accountId
                            account?.let {
                                _uiState.value = uiState.value.copy(
                                    nominal = account.nominal,
                                    name = account.name,
                                    desc = account.desc,
                                    iconPath = account.iconPath,
                                    bgColor = account.bgColor
                                )
                            }
                        }

                        result.onFailure { throwable, message ->
                            _uiState.value = uiState.value.copy(errorMessage = message)
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailAccountEvent) {
        when (event) {
            is DetailAccountEvent.OnDescriptionChange -> _uiState.value = uiState.value.copy(desc = event.text)
            is DetailAccountEvent.OnNameChange -> _uiState.value = uiState.value.copy(name = event.text)
            is DetailAccountEvent.OnSaldoChange -> _uiState.value = uiState.value.copy(nominal = event.text.toLongOrNull() ?: 0)
            is DetailAccountEvent.OnPickAccountIcon -> _uiState.value = uiState.value.copy(iconPath = event.iconPath, bgColor = event.bgColor)
            is DetailAccountEvent.SaveAccount -> upsertAccount()
        }
    }

    private fun upsertAccount() {
        viewModelScope.launch {
            useCase.upsertAccount(
                Account(
                    id = currentAccountId ?: 0,
                    nominal = uiState.value.nominal,
                    name = uiState.value.name,
                    desc = uiState.value.desc,
                    updatedAt = DateUtils.getCurrentDate(),
                    iconPath = uiState.value.iconPath,
                    bgColor = uiState.value.bgColor
                )
            ).collect { result ->
                _upsertState.send(result)
            }
        }
    }

    private fun getAccountCategories() {
        viewModelScope.launch {
            useCase.getAccountCategories().collect { result ->
                result.onSuccess { categories ->
                    categories?.let {
                        _uiState.value = uiState.value.copy(categories = categories)
                    }
                }

                result.onFailure { throwable, message ->
                    _uiState.value = uiState.value.copy(errorMessage = message)
                }
            }
        }
    }
}