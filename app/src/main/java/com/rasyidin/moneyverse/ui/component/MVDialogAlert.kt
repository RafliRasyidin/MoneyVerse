package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.rasyidin.moneyverse.ui.theme.ColorGray300
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

@Composable
fun MVDialogAlert(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    showDialog: Boolean,
    positiveText: String,
    negativeText: String,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit
    ),
    onDismiss: () -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {
    val isShow by remember { mutableStateOf(showDialog) }
    if (isShow) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = properties
        ) {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 16.dp, end = 12.dp),
                        text = title,
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = ColorGray300)
                        .height(1.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = desc,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                onNegativeClick.invoke()
                            }.padding(top = 12.dp, end = 6.dp, start = 12.dp, bottom = 12.dp),
                            text = negativeText,
                            style = MaterialTheme.typography.h4,
                            color = ColorGray500
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            modifier = Modifier.clickable {
                                onPositiveClick.invoke()
                            }.padding(top = 12.dp, end = 12.dp, start = 6.dp, bottom = 12.dp),
                            text = positiveText,
                            style = MaterialTheme.typography.h4,
                            color = ColorPurple500
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewMVDialog() {
    MoneyVerseTheme {
        MVDialogAlert(
            title = "Title",
            desc = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            showDialog = true,
            positiveText = "Ok",
            negativeText = "Cancel",
            onPositiveClick = {},
            onNegativeClick = {},
            onDismiss = {}
        )
    }
}