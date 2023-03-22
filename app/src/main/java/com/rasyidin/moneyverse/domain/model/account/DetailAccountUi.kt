package com.rasyidin.moneyverse.domain.model.account

import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen

data class DetailAccountUi(
    var account: Account = Account(
        id = 0,
        nominal = 0,
        name = "",
        desc = "",
        updatedAt = "",
        R.drawable.ic_cash,
        ColorBgGreen.toArgb()
    ),
    var categories: List<Category> = emptyList(),
    var errorMessage: String? = ""
)
