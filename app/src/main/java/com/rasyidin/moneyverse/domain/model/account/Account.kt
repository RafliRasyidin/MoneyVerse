package com.rasyidin.moneyverse.domain.model.account

import androidx.compose.ui.graphics.toArgb
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.data.local.entities.account.AccountEntity
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen

data class Account(
    val id: Int = 0,
    val nominal: Long = 0,
    val name: String = "",
    val desc: String? = null,
    val updatedAt: String = "",
    val iconPath: Int = R.drawable.ic_cash,
    val bgColor: Int = ColorBgGreen.toArgb()
) {
    fun toEntity() = AccountEntity(
        id = id,
        nominal = nominal,
        name = name,
        desc = desc,
        updatedAt = updatedAt,
        iconPath = iconPath,
        bgColor = bgColor
    )
}
