@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

package com.rasyidin.moneyverse.ui.screen.transaction.add.income

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVButtonPrimary
import com.rasyidin.moneyverse.ui.component.MVTextFieldNominal
import com.rasyidin.moneyverse.ui.component.SheetContentCalendar
import com.rasyidin.moneyverse.ui.component.SheetContentCategories
import com.rasyidin.moneyverse.ui.screen.transaction.add.CardAccount
import com.rasyidin.moneyverse.ui.screen.transaction.add.CardCalendar
import com.rasyidin.moneyverse.ui.screen.transaction.add.CardCategory
import com.rasyidin.moneyverse.ui.screen.transaction.add.TextFieldDesc
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.DateUtils.formatDate
import kotlinx.coroutines.launch

@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    viewModel: IncomeViewModel = hiltViewModel()
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
                SheetIncomeEvent.Idle -> {}
                SheetIncomeEvent.ShowSheetAccounts, SheetIncomeEvent.ShowSheetCategories -> {
                    SheetContentCategories(
                        title = if (viewModel.sheetState.value == SheetIncomeEvent.ShowSheetCategories) stringResource(
                            id = R.string.pilih_kategori
                        ) else stringResource(id = R.string.pilih_akun),
                        categories = when (viewModel.sheetState.value) {
                            SheetIncomeEvent.ShowSheetCategories -> uiState.categories
                            SheetIncomeEvent.ShowSheetAccounts -> uiState.accounts
                            else -> emptyList()
                        },
                        isShowName = true,
                        onItemClick = { category ->
                            coroutineScope.launch {
                                if (viewModel.sheetState.value == SheetIncomeEvent.ShowSheetAccounts) {
                                    viewModel.onEvent(
                                        IncomeEvent.OnSelectAccount(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                } else {
                                    viewModel.onEvent(
                                        IncomeEvent.OnSelectCategory(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                }
                                modalSheetState.hide()
                                viewModel.onEvent(IncomeEvent.HideSheet)
                            }
                        },
                        onCloseClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(IncomeEvent.HideSheet)
                            }
                        }
                    )
                }
                SheetIncomeEvent.ShowSheetCalendar -> {
                    SheetContentCalendar(
                        onSelectedDateClick = { value ->
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(IncomeEvent.OnSelectDate(value))
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
                         viewModel.onEvent(IncomeEvent.OnNominalChange(newText))
                     }
                 )
                 Spacer(modifier = Modifier.height(20.dp))
                 CardCategory(
                     name = uiState.categoryName,
                     label =  stringResource(id = R.string.kategori_pemasukan),
                     bgColor = uiState.categoryBgColor,
                     iconPath = uiState.categoryIconPath,
                     onClick = {
                         viewModel.onEvent(IncomeEvent.ShowSheetCategories)
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
                     name = uiState.accountName,
                     label = stringResource(id = R.string.akun),
                     bgColor = uiState.accountBgColor,
                     iconPath = uiState.accountIconPath,
                     onClick = {
                         viewModel.onEvent(IncomeEvent.ShowSheetAccounts)
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
                         viewModel.onEvent(IncomeEvent.ShowSheetCalendar)
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
                         viewModel.onEvent(IncomeEvent.OnNotesChange(newText))
                     }
                 )
                 Spacer(modifier = Modifier.weight(1F))
                 MVButtonPrimary(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 12.dp),
                     onClick = {
                         viewModel.onEvent(IncomeEvent.Save)
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

@Preview(showBackground = true)
@Composable
private fun PreviewIncomeScreen() {
    MoneyVerseTheme {
        IncomeScreen()
    }
}