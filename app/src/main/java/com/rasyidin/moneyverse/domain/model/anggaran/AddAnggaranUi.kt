package com.rasyidin.moneyverse.domain.model.anggaran

import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import com.rasyidin.moneyverse.utils.DateUtils

data class AddAnggaranUi(
    var id: String = "",
    var nominal: Long = 0,
    var categoryId: Int = -1,
    var categoryName: String = "",
    var categoryIconPath: Int = R.drawable.ic_tagihan,
    var categoryBgColor: Int = ColorBgPurple.toArgb(),
    var anggaranType: AnggaranType = AnggaranType.MONTHLY,
    var anggaranName: String = "",
    var anggaranBgColor: Int = ColorBgPurple.toArgb(),
    var anggaranIconPath: Int = R.drawable.ic_calendar_monthly,
    var startDate: String = DateUtils.getCurrentDate(),
    var endDate: String = "",
    var anggaranBerulang: Boolean = false,
    var categories: List<Category> = emptyList(),
    var anggaranTypes: List<ItemAnggaranType> = emptyList(),
    var buttonState: Boolean = false
)
