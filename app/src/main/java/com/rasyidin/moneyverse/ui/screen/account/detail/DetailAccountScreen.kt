package com.rasyidin.moneyverse.ui.screen.account.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVTextField
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.component.SheetContentCategories
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailAccountViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.value
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            SheetContentCategories(
                title = stringResource(id = R.string.pilih_icon),
                categories = uiState.categories,
                onItemClick = { category ->
                    coroutineScope.launch {
                        modalSheetState.hide()
                        viewModel.onEvent(
                            DetailAccountEvent.OnPickAccountIcon(
                                category.iconPath,
                                category.bgColor
                            )
                        )
                    }
                },
                isShowName = false,
                onCloseClick = {
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                }
            )
        },
        content = {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                MVToolbar(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    title = stringResource(id = R.string.tambah_akun),
                    onBackClick = onBackClick
                )
                PickAddAccountIcon(
                    iconPath = uiState.iconPath,
                    bgColor = uiState.bgColor,
                    onClick = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }
                    }
                )
                TextFieldAccount(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    name = uiState.name,
                    desc = uiState.desc ?: "",
                    nominal = uiState.nominal.toString(),
                    onNameChange = { newText ->
                        viewModel.onEvent(DetailAccountEvent.OnNameChange(newText))
                    },
                    onDescChange = { newText ->
                        viewModel.onEvent(DetailAccountEvent.OnDescriptionChange(newText))
                    },
                    onNominalChange = { newText ->
                        viewModel.onEvent(DetailAccountEvent.OnSaldoChange(newText))
                    }
                )
                Spacer(modifier = Modifier.weight(1F))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    onClick = {
                        viewModel.onEvent(DetailAccountEvent.SaveAccount)
                    }
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
fun PickAddAccountIcon(
    modifier: Modifier = Modifier,
    iconPath: Int? = null,
    bgColor: Int? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color(bgColor ?: ColorBgGreen.toArgb()), shape = CircleShape)
                .padding(36.dp)
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = iconPath ?: R.drawable.ic_cash),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.pilih_icon),
            style = MaterialTheme.typography.h6
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldAccount(
    modifier: Modifier = Modifier,
    name: String,
    desc: String,
    nominal: String,
    onNameChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onNominalChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MVTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onNameChange,
            label = stringResource(id = R.string.nama_akun),
            singleLine = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            value = name,
            onKeyboardActionClick = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        MVTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onDescChange,
            label = stringResource(id = R.string.deskripsi),
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            value = desc,
            onKeyboardActionClick = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
        MVTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onNominalChange,
            label = stringResource(id = R.string.jumlah_saldo),
            singleLine = true,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            prefix = "Rp",
            onKeyboardActionClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            value = nominal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDetailAccountScreen() {
    MoneyVerseTheme {
        DetailAccountScreen(onBackClick = {})
    }
}