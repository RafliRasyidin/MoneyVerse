package com.rasyidin.moneyverse.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.*
import java.text.DecimalFormat
import java.text.NumberFormat

@Composable
fun MVTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    hint: String = "",
    value: String = "",
    prefix: String = "",
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    maxLength: Int = Int.MAX_VALUE,
    imeAction: ImeAction = ImeAction.Next,
    @DrawableRes iconEnd: Int? = null,
    onValueChange: (String) -> Unit,
    onKeyboardActionClick: () -> Unit = {},
    onIconEndClick: () -> Unit = {},
) {
    var text by remember { mutableStateOf(value) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorGray200, MaterialTheme.shapes.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = prefix.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = prefix,
                    style = MaterialTheme.typography.subtitle1,
                    color = if (text.isNotEmpty()) ColorGray500 else ColorGray300
                )
            }
            TextField(
                modifier = Modifier.weight(1F),
                value = value,
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        text = newText
                        onValueChange.invoke(text)
                    }
                },
                textStyle = MaterialTheme.typography.subtitle1,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = ColorGray500,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = ColorPurple500,
                    backgroundColor = Color.Transparent
                ),
                enabled = enabled,
                maxLines = maxLines,
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    capitalization = capitalization
                ),
                keyboardActions = KeyboardActions {
                    onKeyboardActionClick.invoke()
                },
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.subtitle1,
                        color = ColorGray300
                    )
                },
                visualTransformation = if (keyboardType == KeyboardType.Number) ThousandSeparatorTransformation() else VisualTransformation.None
            )
            AnimatedVisibility(visible = text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            text = ""
                            onValueChange.invoke(text)
                        }
                        .padding(end = 12.dp)
                )
            }
            AnimatedVisibility(visible = iconEnd != null) {
                Icon(
                    painter = painterResource(id = iconEnd!!),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onIconEndClick.invoke()
                        }
                        .padding(end = 12.dp)

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVTextField() {
    MoneyVerseTheme {
        MVTextField(
            label = "Label",
            hint = "Hint",
            value = "",
            prefix = "Rp",
            onValueChange = {}
        )
    }
}

class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val symbols = DecimalFormat().decimalFormatSymbols
        val decimalSeparator = symbols.decimalSeparator

        var outputText = ""
        val integerPart: Long
        val decimalPart: String

        if (text.text.isNotEmpty()) {
            val number = text.text.toDouble()
            integerPart = number.toLong()
            outputText += NumberFormat.getIntegerInstance().format(integerPart)
            if (text.text.contains(decimalSeparator)) {
                decimalPart = text.text.substring(text.text.indexOf(decimalSeparator))
                if (decimalPart.isNotEmpty()) {
                    outputText += decimalPart
                }
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return outputText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.length
            }
        }

        return TransformedText(
            text = AnnotatedString(outputText),
            offsetMapping = numberOffsetTranslator
        )
    }
}