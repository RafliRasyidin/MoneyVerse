package com.rasyidin.moneyverse.ui.screen.anggaran

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranUi
import com.rasyidin.moneyverse.domain.model.anggaran.ItemAnggaran
import com.rasyidin.moneyverse.ui.component.MVButtonPrimary
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.AnggaranUtils
import com.rasyidin.moneyverse.utils.ExpenditureCategory.*
import com.rasyidin.moneyverse.utils.dropShadow
import com.rasyidin.moneyverse.utils.toCurrency

@Composable
fun AnggaranScreen(
    modifier: Modifier = Modifier
) {
    val listAnggaran = mutableListOf(
        ItemAnggaran(
            categoryName = "Tagihan",
            categoryIconPath = R.drawable.ic_tagihan,
            categoryBgPath = ColorBgPurple.toArgb(),
            avgExpensePerDay = 7000,
            sisaAnggaran = 100000,
            totalAnggaran = 1000000,
            totalOutcome = 900000
        ),
        ItemAnggaran(
            categoryName = "Makan & Minum",
            categoryIconPath = R.drawable.ic_food_n_drink,
            categoryBgPath = ColorBgRed.toArgb(),
            avgExpensePerDay = 23000,
            sisaAnggaran = 400000,
            totalAnggaran = 500000,
            totalOutcome = 100000
        ),
        ItemAnggaran(
            categoryName = "Makan & Minum",
            categoryIconPath = R.drawable.ic_asuransi,
            categoryBgPath = ColorBgPurple.toArgb(),
            avgExpensePerDay = 23000,
            sisaAnggaran = 0,
            totalAnggaran = 500000,
            totalOutcome = 600000
        ),
    )
    AnggaranContent(modifier = modifier, anggaranUi = AnggaranUi(listAnggaran = listAnggaran))
}

@Composable
fun AnggaranContent(
    modifier: Modifier = Modifier,
    anggaranUi: AnggaranUi
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToolbarAnggaran(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
            dateFilter = anggaranUi.dateFilter,
            onFilterClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        SaldoAnggaran(
            onAddClick = {},
            sisaAnggaran = anggaranUi.sisaAnggaran,
            totalAnggaran = anggaranUi.totalAnggaran
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(id = R.string.daftar_anggaran),
            style = MaterialTheme.typography.h6,
            color = ColorBlack
        )
        ListAnggaran(listAnggaran = anggaranUi.listAnggaran)
    }
}

@Composable
fun ToolbarAnggaran(
    modifier: Modifier = Modifier,
    dateFilter: String,
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
                text = dateFilter,
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
    sisaAnggaran: Long,
    totalAnggaran: Long,
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
                text = sisaAnggaran.toCurrency(),
                style = MaterialTheme.typography.h1
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.count_sisa_anggaran, totalAnggaran.toCurrency()),
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
    modifier: Modifier = Modifier,
    listAnggaran: List<ItemAnggaran>
) {
    LazyColumn(
        modifier = modifier.padding(PaddingValues(horizontal = 12.dp))
    ) {
        items(listAnggaran) { itemAnggaran ->
            ItemAnggaran(
                modifier = Modifier.padding(PaddingValues(vertical = 12.dp)),
                item = itemAnggaran,
                onItemClick = {}
            )
        }
    }
}

@Composable
fun ItemAnggaran(
    modifier: Modifier = Modifier,
    item: ItemAnggaran,
    onItemClick: () -> Unit
) {
    val animDuration = 750
    val expenditureCategory =
        AnggaranUtils.getExpenditureRange(item.totalAnggaran, item.totalOutcome)
    var animBarPlayed by remember { mutableStateOf(false) }
    val expenditureWithAnimation by animateFloatAsState(
        targetValue = if (animBarPlayed) {
            item.totalOutcome.toFloat() / item.totalAnggaran.toFloat()
        } else 0F,
        animationSpec = tween(
            durationMillis = animDuration,
            easing = FastOutSlowInEasing
        )
    )
    LaunchedEffect(true) {
        animBarPlayed = true
    }
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
                            color = Color(item.categoryBgPath),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(10.dp)
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(item.categoryIconPath),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.weight(1F),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = item.categoryName,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Rp ${item.avgExpensePerDay.toCurrency()}/Hari",
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ColorGray200)
            )
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
                        text = "Rp. ${item.sisaAnggaran.toCurrency()}",
                        style = MaterialTheme.typography.h6,
                        color = when (expenditureCategory) {
                            DANGER -> ColorRed500
                            WARNING -> ColorBlue500
                            SAFE -> ColorGreen500
                        }
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
                        text = "Rp. ${item.totalAnggaran.toCurrency()}",
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
                        .fillMaxWidth(expenditureWithAnimation)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(
                            color = when (expenditureCategory) {
                                DANGER -> ColorRed500
                                WARNING -> ColorBlue500
                                SAFE -> ColorGreen500
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(
                        id = when (expenditureCategory) {
                            DANGER -> R.drawable.ic_boom
                            WARNING -> R.drawable.ic_fire
                            SAFE -> R.drawable.ic_sign_ok
                        }
                    ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(
                        id = when (expenditureCategory) {
                            DANGER -> R.string.anggaran_bahaya
                            WARNING -> R.string.anggaran_warning
                            SAFE -> R.string.anggaran_masih_batas_wajar
                        }
                    ),
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorGray500
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAnggaranContent() {
    MoneyVerseTheme {
        val listAnggaran = mutableListOf(
            ItemAnggaran(
                categoryName = "Tagihan",
                categoryIconPath = R.drawable.ic_tagihan,
                categoryBgPath = ColorBgPurple.toArgb(),
                avgExpensePerDay = 7000,
                sisaAnggaran = 100000,
                totalAnggaran = 1000000
            ),
            ItemAnggaran(
                categoryName = "Makan & Minum",
                categoryIconPath = R.drawable.ic_food_n_drink,
                categoryBgPath = ColorBgRed.toArgb(),
                avgExpensePerDay = 23000,
                sisaAnggaran = 400000,
                totalAnggaran = 500000
            ),
        )
        AnggaranContent(
            anggaranUi = AnggaranUi(
                sisaAnggaran = 2000000,
                totalAnggaran = 5000000,
                dateFilter = "Juni 2022",
                listAnggaran = listAnggaran
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewItemAnggaran() {
    MoneyVerseTheme {
        ItemAnggaran(
            item = ItemAnggaran(
                categoryName = "Makan & Minum",
                categoryIconPath = R.drawable.ic_food_n_drink,
                categoryBgPath = ColorBgRed.toArgb(),
                avgExpensePerDay = 23000,
                sisaAnggaran = 100000,
                totalAnggaran = 500000,
                totalOutcome = 400000
            ),
            onItemClick = {}
        )
    }
}