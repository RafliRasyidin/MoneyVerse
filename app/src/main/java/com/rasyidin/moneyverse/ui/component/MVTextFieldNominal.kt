package com.rasyidin.moneyverse.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorBlack
import com.rasyidin.moneyverse.ui.theme.ColorGray300
import com.rasyidin.moneyverse.ui.theme.ColorPurple500

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MVTextFieldNominal(
    modifier: Modifier = Modifier,
    nominal: String,
    onNominalChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(nominal) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.offset(y = (14).dp),
            text = stringResource(id = R.string.nominal),
            style = MaterialTheme.typography.h6,
            color = ColorBlack,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Rp",
                style = MaterialTheme.typography.subtitle1,
                color = ColorBlack,
            )
            TextField(
                modifier = Modifier
                    .weight(1F)
                    .offset(y = 14.dp),
                value = text,
                onValueChange = { newText ->
                    text = newText
                    onNominalChange.invoke(text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = ColorBlack,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = ColorPurple500,
                    backgroundColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.h1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
                placeholder = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.h1,
                        color = ColorGray300
                    )
                },
                visualTransformation = ThousandSeparatorTransformation()
            )
            AnimatedVisibility(visible = text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                        .padding(end = 12.dp)
                        .offset(y = (-14).dp)
                        .clickable {
                            text = ""
                            onNominalChange.invoke(text)
                        }
                )
            }
        }
    }
}