package com.rasyidin.moneyverse.data.local.entities.anggaran

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anggaran")
data class AnggaranEntity(
    @PrimaryKey
    var id: String,
    var transactionId: String,
)
