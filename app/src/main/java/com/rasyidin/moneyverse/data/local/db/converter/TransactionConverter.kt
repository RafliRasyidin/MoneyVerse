package com.rasyidin.moneyverse.data.local.db.converter

import androidx.room.TypeConverter
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType

class TransactionConverter {

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return when (value) {
            TransactionType.INCOME.name -> TransactionType.INCOME
            TransactionType.OUTCOME.name -> TransactionType.OUTCOME
            TransactionType.TRANSFER.name -> TransactionType.TRANSFER
            else -> throw IllegalStateException("Illegal transaction type, expected TransactionType but found $value")
        }
    }

    @TypeConverter
    fun fromTransactionType(transactionType: TransactionType): String {
        return when (transactionType) {
            TransactionType.INCOME -> TransactionType.INCOME.name
            TransactionType.OUTCOME -> TransactionType.OUTCOME.name
            TransactionType.TRANSFER -> TransactionType.TRANSFER.name
        }
    }
}