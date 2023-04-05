package com.rasyidin.moneyverse.domain.model.anggaran

import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple

data class ItemAnggaran(
    var id: String = "",
    var categoryId: Int = -1,
    var categoryIconPath: Int = R.drawable.ic_tagihan,
    var categoryBgPath: Int = ColorBgPurple.toArgb(),
    var categoryName: String = "",
    var avgExpensePerDay: Long = 0,
    var sisaAnggaran: Long = 0,
    var totalAnggaran: Long = 0,
    var totalOutcome: Long = 0
)
