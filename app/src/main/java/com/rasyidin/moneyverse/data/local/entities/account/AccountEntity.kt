package com.rasyidin.moneyverse.data.local.entities.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.moneyverse.domain.model.account.Account

@Entity("account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val nominal: Long,
    val updatedAt: String,
    val iconPath: Int,
    val bgColor: Int,
    val desc: String? = ""
) {
    fun toDomain() = Account(
        id = id,
        name = name,
        nominal = nominal,
        desc = desc,
        updatedAt = updatedAt,
        iconPath = iconPath,
        bgColor = bgColor
    )
}
