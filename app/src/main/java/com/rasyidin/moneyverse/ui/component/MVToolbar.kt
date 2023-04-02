package com.rasyidin.moneyverse.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

@Composable
fun MVToolbar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    onIconEndClick: (Int) -> Unit = {},
    iconsEnd: List<Int> = emptyList()
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
                .clickable { onBackClick.invoke() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1F),
            text = title,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        AnimatedVisibility(visible = iconsEnd.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                iconsEnd.forEachIndexed { position, resId ->
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onIconEndClick.invoke(position) },
                        painter = painterResource(id = resId),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVToolbar() {
    MoneyVerseTheme {
        MVToolbar(
            title = "Toolbar",
            onBackClick = {},
            iconsEnd = listOf(R.drawable.ic_chat, R.drawable.ic_notif)
        )
    }
}