package com.rasyidin.moneyverse.domain.model.account

import com.rasyidin.moneyverse.data.local.entities.account.AccountEntity

data class Account(
    val id: Int,
    val nominal: Long,
    val name: String,
    val desc: String? = null,
    val updatedAt: String,
    val iconPath: Int,
    val bgColor: Int
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
