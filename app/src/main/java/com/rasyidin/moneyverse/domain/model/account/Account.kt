package com.rasyidin.moneyverse.domain.model.account

data class Account(
    val id: Int,
    val nominal: Long,
    val name: String,
    val desc: String? = null,
    val updatedAt: String,
    val iconPath: Int,
    val bgColor: Int
)
