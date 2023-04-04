package com.rasyidin.moneyverse.ui.screen.anggaran

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVButtonPrimary
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.dropShadow

@Composable
fun AnggaranScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToolbarAnggaran(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
            onFilterClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        SaldoAnggaran(onAddClick = {})
        Spacer(modifier = Modifier.height(16.dp))
        ListAnggaran()
    }
}

@Composable
fun ToolbarAnggaran(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.anggaran),
            style = MaterialTheme.typography.h3
        )
        Row(
            modifier = Modifier
                .clickable { onFilterClick.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Juni",
                style = MaterialTheme.typography.h6,
                color = ColorPurple500
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.ic_downward),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorPurple500)
            )
        }
    }
}

@Composable
fun SaldoAnggaran(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rp",
                style = MaterialTheme.typography.h2,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "2.280.000",
                style = MaterialTheme.typography.h1
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.count_sisa_anggaran),
            style = MaterialTheme.typography.subtitle2,
            color = ColorGray500,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(36.dp))
        MVButtonPrimary(
            onClick = onAddClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.buat_anggaran_baru),
                style = MaterialTheme.typography.h6,
                color = ColorWhite
            )
        }
    }
}

@Composable
fun ListAnggaran(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(PaddingValues(12.dp))
    ) {

    }
}

@Composable
fun ItemAnggaran(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable { onItemClick.invoke() }
            .dropShadow(),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = ColorBgPurple,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(10.dp)
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_tagihan),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.weight(1F),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Tagihan",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Rp 66.000/Hari",
                        style = MaterialTheme.typography.subtitle2,
                        color = ColorGray500
                    )
                }
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_forward),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ColorGray200))
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sisa),
                        style = MaterialTheme.typography.subtitle2,
                        color = ColorGray500
                    )
                    Text(
                        text = "Rp. 900.000",
                        style = MaterialTheme.typography.h6,
                        color = ColorGreen100
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.anggaran),
                        style = MaterialTheme.typography.subtitle2,
                        color = ColorGray500
                    )
                    Text(
                        text = "Rp. 2.000.000",
                        style = MaterialTheme.typography.h6,
                        color = ColorBlack
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(ColorGray200)
            ) {
                Box(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth(.5F)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(ColorGreen500)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_sign_ok),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.anggaran_masih_batas_wajar),
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorGray500
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAnggaranScreen() {
    MoneyVerseTheme {
        AnggaranScreen()
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewItemAnggaran() {
    MoneyVerseTheme {
        ItemAnggaran(onItemClick = {})
    }
}