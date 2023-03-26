package com.rasyidin.moneyverse.ui.screen.transaction.add.transfer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVButtonPrimary
import com.rasyidin.moneyverse.ui.component.MVTextFieldNominal
import com.rasyidin.moneyverse.ui.component.SheetContentCalendar
import com.rasyidin.moneyverse.ui.component.SheetContentCategories
import com.rasyidin.moneyverse.ui.screen.transaction.add.CardAccount
import com.rasyidin.moneyverse.ui.screen.transaction.add.CardCalendar
import com.rasyidin.moneyverse.ui.screen.transaction.add.TextFieldDesc
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.utils.DateUtils.formatDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TransferScreen(
    modifier: Modifier = Modifier,
    viewModel: TransferViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.value
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            when (viewModel.sheetState.value) {
                SheetTransferEvent.Idle -> {}
                SheetTransferEvent.ShowSheetFromAccount, SheetTransferEvent.ShowSheetToAccount -> {
                    SheetContentCategories(
                        title = stringResource(id = R.string.pilih_akun),
                        categories = uiState.accounts,
                        isShowName = true,
                        onItemClick = { category ->
                            coroutineScope.launch {
                                if (viewModel.sheetState.value == SheetTransferEvent.ShowSheetFromAccount) {
                                    if (category.id == uiState.toAccountId) {
                                        viewModel.onEvent(TransferEvent.ReverseAccount(true))
                                    }
                                    viewModel.onEvent(
                                        TransferEvent.OnSelectFromAccount(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                } else {
                                    if (category.id == uiState.fromAccountId) {
                                        viewModel.onEvent(TransferEvent.ReverseAccount(false))
                                    }
                                    viewModel.onEvent(
                                        TransferEvent.OnSelectToAccount(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                }
                                modalSheetState.hide()
                                viewModel.onEvent(TransferEvent.HideSheet)
                            }
                        },
                        onCloseClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(TransferEvent.HideSheet)
                            }
                        }
                    )
                }
                SheetTransferEvent.ShowSheetCalendar -> {
                    SheetContentCalendar(
                        onSelectedDateClick = { value ->
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(TransferEvent.OnSelectDate(value))
                            }
                        }
                    )
                }
            }
        },
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                MVTextFieldNominal(
                    nominal = uiState.nominal.toString(),
                    onNominalChange = { newText ->
                        viewModel.onEvent(TransferEvent.OnNominalChange(newText))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                CardAccount(
                    name = uiState.fromAccountName,
                    label =  stringResource(id = R.string.dari_akun),
                    bgColor = uiState.fromAccountBgColor,
                    iconPath = uiState.fromAccountIconPath,
                    onClick = {
                        viewModel.onEvent(TransferEvent.ShowSheetFromAccount)
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                modalSheetState.show()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
                CardAccount(
                    name = uiState.toAccountName,
                    label = stringResource(id = R.string.ke_akun),
                    bgColor = uiState.toAccountBgColor,
                    iconPath = uiState.toAccountIconPath,
                    onClick = {
                        viewModel.onEvent(TransferEvent.ShowSheetToAccount)
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                modalSheetState.show()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
                CardCalendar(
                    date = uiState.date.formatDate(),
                    onClick = {
                        viewModel.onEvent(TransferEvent.ShowSheetCalendar)
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                modalSheetState.show()
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
                TextFieldDesc(
                    desc = uiState.notes,
                    onDescChange = { newText ->
                        viewModel.onEvent(TransferEvent.OnNotesChange(newText))
                    }
                )
                Spacer(modifier = Modifier.weight(1F))
                MVButtonPrimary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    onClick = {
                        viewModel.onEvent(TransferEvent.Save)
                    },
                    enabled = viewModel.buttonState,
                ) {
                    Text(
                        text = stringResource(id = R.string.simpan),
                        style = MaterialTheme.typography.button,
                        color = ColorWhite
                    )
                }
            }
        }
    )
}

