package com.rasyidin.moneyverse.domain.model.transaction

import androidx.annotation.DrawableRes
import com.rasyidin.moneyverse.utils.DateUtils

data class DetailTransactionUi(
    var id: Int = -1,
    var nominal: Long = 0,
    var createdAt: String = DateUtils.getCurrentDate(),
    var notes: String? = "",
    var transactionType: TransactionType = TransactionType.OUTCOME,
    var categoryId: Int? = -1,
    var fromAccountId: Int = -1,
    var toAccountId: Int? = -1,
    var accountName: String = "",
    @DrawableRes var accountIconPath: Int = -1,
    @DrawableRes var accountBgColor: Int = -1,
    var categoryName: String? = "",
    @DrawableRes var categoryIconPath: Int? = -1,
    @DrawableRes var categoryBgColor: Int? = -1
)
