package com.rasyidin.moneyverse.domain.model.transaction

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import com.rasyidin.moneyverse.utils.DateUtils

data class TransferUi(
    var id: Int = 0,
    var nominal: Long = 0,
    var date: String = DateUtils.getCurrentDate(),
    var notes: String = "",
    var fromAccountId: Int = -1,
    @DrawableRes var fromAccountIconPath: Int = R.drawable.ic_tagihan,
    var fromAccountBgColor: Int = ColorBgPurple.toArgb(),
    var fromAccountName: String = "",
    var toAccountId: Int = -1,
    @DrawableRes var toAccountIconPath: Int = -1,
    var toAccountBgColor: Int = -1,
    var toAccountName: String = "",
    var accounts: List<Category> = emptyList(),
    var errorMessage: String? = ""
)
