package com.rasyidin.moneyverse.ui.screen.account.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.domain.*
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.domain.model.account.DetailAccountUi
import com.rasyidin.moneyverse.domain.usecase.account.AccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAccountViewModel @Inject constructor(private val useCase: AccountUseCase): ViewModel() {

    private var _uiState = mutableStateOf(DetailAccountUi())
    val uiState: State<DetailAccountUi> get() = _uiState

    private var _upsertState: Channel<ResultState<Nothing>> = idleChannel()
    val upsertState get() = _upsertState.receiveAsFlow()

    private var _detailAccountState: MutableStateFlow<ResultState<Account>> = idleStateFlow()
    val detailAccountState get() = _detailAccountState

    init {
        getAccountCategories()
    }

    fun upsertAccount(account: Account) {
        viewModelScope.launch {
            useCase.upsertAccount(account).collect { result ->
                _upsertState.send(result)
            }
        }
    }

    fun getDetailAccount(accountId: Int) {
        viewModelScope.launch {
            useCase.getDetailAccount(accountId).collect { result ->
                result.onSuccess { account ->
                    account?.let {
                        _uiState.value = uiState.value.copy(account = account)
                    }
                }

                result.onFailure { throwable, message ->
                    _uiState.value = uiState.value.copy(errorMessage = message)
                }
            }
        }
    }

    fun getAccountCategories() {
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