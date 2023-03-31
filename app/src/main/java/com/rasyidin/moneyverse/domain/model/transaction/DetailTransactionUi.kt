package com.rasyidin.moneyverse.domain.model.transaction

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.ColorGray400
import com.rasyidin.moneyverse.utils.DateUtils

data class DetailTransactionUi(
    var id: Int = -1,
    var nominal: Long = 0,
    var createdAt: String = DateUtils.getCurrentDate(),
    var notes: String? = null,
    var transactionType: TransactionType = TransactionType.OUTCOME,
    var categoryId: Int? = null,
    var fromAccountId: Int = -1,
    var toAccountId: Int? = null,
    var accountName: String = "",
    @DrawableRes var accountIconPath: Int = R.drawable.ic_cash,
    @DrawableRes var accountBgColor: Int = ColorBgGreen.toArgb(),
    var categoryName: String? = "",
    @DrawableRes var categoryIconPath: Int? = null,
    @DrawableRes var categoryBgColor: Int? = null,
    var dominantColorFromAccount: Int? = ColorGray400.toArgb(),
    var dominantColorToAccount: Int? = ColorGray400.toArgb()
)
