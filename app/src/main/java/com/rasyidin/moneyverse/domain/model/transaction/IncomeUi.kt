package com.rasyidin.moneyverse.domain.model.transaction

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import com.rasyidin.moneyverse.utils.DateUtils

data class IncomeUi(
    var id: Int = 0,
    var nominal: Long = 0,
    var date: String = DateUtils.getCurrentDate(),
    var notes: String = "",
    var categoryId: Int = -1,
    @DrawableRes var categoryIconPath: Int = R.drawable.ic_tagihan,
    var categoryBgColor: Int = ColorBgPurple.toArgb(),
    var categoryName: String = "",
    var accountId: Int = -1,
    @DrawableRes var accountIconPath: Int = -1,
    var accountBgColor: Int = -1,
    var accountName: String = "",
    var accounts: List<Category> = emptyList(),
    var categories: List<Category> = emptyList(),
    var errorMessage: String? = ""
)
