package com.rasyidin.moneyverse.data.local.entities.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType

@Entity(tableName = "transaksi")
data class TransactionEntity(
    @PrimaryKey(true)
    var id: Int = 0,

    @ColumnInfo("nominal")
    var nominal: Long,

    @ColumnInfo("createdAt")
    var createdAt: String,

    @ColumnInfo("notes")
    var notes: String? = null,

    @ColumnInfo("transactionType")
    var transactionType: TransactionType = TransactionType.OUTCOME,

    @ColumnInfo("categoryId")
    var categoryId: Int,

    @ColumnInfo("sumberAkun")
    var sourceAccountId: Int,

    @ColumnInfo("tujuanAkun")
    var destinationAccountId: Int? = null,
)
