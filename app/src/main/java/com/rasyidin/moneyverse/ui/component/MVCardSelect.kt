package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.dropShadow

@Composable
fun MVCardSelect(
    modifier: Modifier = Modifier,
    label: String,
    name: String,
    bgColor: Int?,
    iconPath: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h6,
            color = ColorBlack
        )
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max)
                .clickable { onClick.invoke() }
                .dropShadow(),
            shape = MaterialTheme.shapes.small,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (bgColor != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                Color(if (iconPath == -1) ColorBgBlue.toArgb() else bgColor),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(12.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = if (iconPath == -1) R.drawable.ic_tagihan else iconPath),
                            contentDescription = null
                        )
                    }
                } else {
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVCardSelect(
    modifier: Modifier = Modifier
) {
    MoneyVerseTheme {
        MVCardSelect(
            label = "Pilih",
            name = "Pilih",
            bgColor = ColorBgPurple.toArgb(),
            iconPath = R.drawable.ic_tagihan,
            onClick = {}
        )
    }
}