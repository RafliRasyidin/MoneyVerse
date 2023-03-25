@file:OptIn(ExperimentalMaterialApi::class)

package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.*
import com.rasyidin.moneyverse.ui.screen.transaction.add.outcome.SheetOutcomeTransactionEvent.*
import com.rasyidin.moneyverse.ui.theme.ColorBgBlue
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.DateUtils.formatDate
import com.rasyidin.moneyverse.utils.dropShadow
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: OutcomeViewModel = hiltViewModel()
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
                Idle -> {}
                ShowSheetAccounts, ShowSheetCategories -> {
                    SheetContentCategories(
                        title = if (viewModel.sheetState.value == ShowSheetCategories) stringResource(
                            id = R.string.pilih_kategori
                        ) else stringResource(id = R.string.pilih_akun),
                        categories = when (viewModel.sheetState.value) {
                            ShowSheetCategories -> uiState.categories
                            ShowSheetAccounts -> uiState.accounts
                            else -> emptyList()
                        },
                        isShowName = true,
                        onItemClick = { category ->
                            coroutineScope.launch {
                                if (viewModel.sheetState.value == ShowSheetAccounts) {
                                    viewModel.onEvent(
                                        OutcomeTransactionEvent.OnSelectAccount(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                } else {
                                    viewModel.onEvent(
                                        OutcomeTransactionEvent.OnSelectCategory(
                                            id = category.id,
                                            iconPath = category.iconPath,
                                            bgColor = category.bgColor,
                                            name = category.name
                                        )
                                    )
                                }
                                modalSheetState.hide()
                                viewModel.onEvent(OutcomeTransactionEvent.HideSheet)
                            }
                        },
                        onCloseClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(OutcomeTransactionEvent.HideSheet)
                            }
                        }
                    )
                }
                ShowSheetCalendar -> {
                    SheetContentCalendar(
                        onSelectedDateClick = { value ->
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(OutcomeTransactionEvent.OnSelectDate(value))
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
                        viewModel.onEvent(OutcomeTransactionEvent.OnNominalChange(newText))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                CardCategory(
                    name = uiState.categoryName,
                    bgColor = uiState.categoryBgColor,
                    iconPath = uiState.categoryIconPath,
                    onClick = {
                        viewModel.onEvent(OutcomeTransactionEvent.ShowSheetCategories)
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
                    bgColor = uiState.accountBgColor,
                    iconPath = uiState.accountIconPath,
                    onClick = {
                        viewModel.onEvent(OutcomeTransactionEvent.ShowSheetAccounts)
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
                        viewModel.onEvent(OutcomeTransactionEvent.ShowSheetCalendar)
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
                        viewModel.onEvent(OutcomeTransactionEvent.OnNotesChange(newText))
                    }
                )
                Spacer(modifier = Modifier.weight(1F))
                MVButtonPrimary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    onClick = {
                        viewModel.onEvent(OutcomeTransactionEvent.SaveTransaction)
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

@Composable
fun CardCategory(
    modifier: Modifier = Modifier,
    name: String,
    bgColor: Int,
    iconPath: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable { onClick.invoke() }
            .dropShadow(),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color(if (iconPath == -1) ColorBgBlue.toArgb() else bgColor), shape = MaterialTheme.shapes.small)
                    .padding(4.dp)
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (iconPath == -1) R.drawable.ic_tagihan else iconPath),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(23.dp))
            Text(
                modifier = Modifier.weight(1F),
                text = name,
                style = MaterialTheme.typography.h6,
                color = ColorGray500
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CardAccount(
    modifier: Modifier = Modifier,
    name: String,
    bgColor: Int,
    iconPath: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable { onClick.invoke() }
            .dropShadow(),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color(if (iconPath == -1) ColorBgBlue.toArgb() else bgColor),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(4.dp)
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (iconPath == -1) R.drawable.ic_tagihan else iconPath),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(23.dp))
            Text(
                modifier = Modifier.weight(1F),
                text = name,
                style = MaterialTheme.typography.h6,
                color = ColorGray500
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CardCalendar(
    modifier: Modifier = Modifier,
    date: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable { onClick.invoke() }
            .dropShadow(),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(23.dp))
            Text(
                modifier = Modifier.weight(1F),
                text = date,
                style = MaterialTheme.typography.h6,
                color = ColorGray500
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null
            )
        }
    }
}

@Composable
fun TextFieldDesc(
    modifier: Modifier = Modifier,
    desc: String,
    onDescChange: (String) -> Unit
) {
    MVTextField(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(),
        value = desc,
        hint = stringResource(id = R.string.desckripsi_singkat),
        iconStart = R.drawable.ic_edit,
        onValueChange = onDescChange
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewOutcomeScreen() {
    MoneyVerseTheme {
        OutcomeScreen()
    }
}