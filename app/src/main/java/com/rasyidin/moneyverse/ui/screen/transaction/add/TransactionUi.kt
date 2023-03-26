package com.rasyidin.moneyverse.ui.screen.transaction.add

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVTextField
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.dropShadow

@Composable
fun CardCategory(
    modifier: Modifier = Modifier,
    label: String,
    name: String,
    bgColor: Int,
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
                Box(
                    modifier = Modifier
                        .background(
                            Color(if (iconPath == -1) ColorBgBlue.toArgb() else bgColor),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(6.dp)
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
}

@Composable
fun CardAccount(
    modifier: Modifier = Modifier,
    label: String,
    name: String,
    bgColor: Int,
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
                Box(
                    modifier = Modifier
                        .background(
                            Color(if (iconPath == -1) ColorBgBlue.toArgb() else bgColor),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(6.dp)
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
}

@Composable
fun CardCalendar(
    modifier: Modifier = Modifier,
    date: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.tanggal),
            style = MaterialTheme.typography.h6,
            color = ColorBlack
        )
        Spacer(modifier = Modifier.height(4.dp))
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
}

@Composable
fun TextFieldDesc(
    modifier: Modifier = Modifier,
    desc: String,
    onDescChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.deskripsi),
            style = MaterialTheme.typography.h6,
            color = ColorBlack
        )
        Spacer(modifier = Modifier.height(4.dp))
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardCategory() {
    MoneyVerseTheme {
        CardCategory(
            label = "Kategori Pengeluaran",
            name = "Pilih Kategori",
            bgColor = ColorBgPurple.toArgb(),
            iconPath = R.drawable.ic_tagihan
        ) {

        }
    }
}