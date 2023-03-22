package com.rasyidin.moneyverse.domain.model.account

import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.utils.DateUtils

data class DetailAccountUi(
    var id: Int = 0,
    var nominal: Long = 0,
    var name: String = "",
    var desc: String? = "",
    var updatedAt: String = DateUtils.getCurrentDate(),
    var iconPath: Int = R.drawable.ic_cash,
    var bgColor: Int = ColorBgGreen.toArgb(),
    var categories: List<Category> = emptyList(),
    var errorMessage: String? = ""
)
