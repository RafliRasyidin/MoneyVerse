package com.rasyidin.moneyverse.ui.screen.account.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.ui.component.MVTextField
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

@Composable
fun DetailAccountScreen(
    modifier: Modifier = Modifier,
    account: Account?,
    onNameChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onNominalChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    var name by remember { mutableStateOf(account?.name ?: "") }
    var desc by remember { mutableStateOf(account?.desc ?: "") }
    var nominal by remember { mutableStateOf((account?.nominal ?: 0).toString()) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MVToolbar(
            modifier = Modifier.padding(horizontal = 12.dp),
            title = "Tambah Akun",
            onBackClick = onBackClick
        )
        PickAddAccountIcon(
            iconPath = account?.iconPath,
            bgColor = account?.bgColor
        )
        TextFieldAccount(
            modifier = Modifier.padding(horizontal = 12.dp),
            name = name,
            desc = desc,
            nominal = nominal,
            onNameChange = { newText ->
                name = newText
                onNameChange.invoke(name)
            },
            onDescChange = { newText ->
                desc = newText
                onDescChange.invoke(desc)
            },
            onNominalChange = { newText ->
                nominal = newText
                onNominalChange.invoke(nominal)
            }
        )
        Spacer(modifier = Modifier.weight(1F))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            onClick = onSaveClick
        ) {
            Text(
                text = stringResource(id = R.string.simpan),
                style = MaterialTheme.typography.button,
                color = ColorWhite
            )
        }
    }
}

@Composable
fun PickAddAccountIcon(
    modifier: Modifier = Modifier,
    iconPath: Int? = null,
    bgColor: Int? = null
) {
    val background by remember { mutableStateOf(bgColor) }
    val icon by remember { mutableStateOf(iconPath) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color(background ?: ColorBgGreen.toArgb()), shape = CircleShape)
                .padding(36.dp)
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = icon ?: R.drawable.ic_cash),
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
        DetailAccountScreen(
            account = Account(
                id = 1,
                nominal = 100000,
                name = "Cash",
                updatedAt = "",
                iconPath = R.drawable.ic_cash,
                bgColor = ColorBgGreen.toArgb()
            ),
            onNominalChange = {},
            onDescChange = {},
            onNameChange = {},
            onBackClick = {},
            onSaveClick = {}
        )
    }
}