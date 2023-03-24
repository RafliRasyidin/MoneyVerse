package com.rasyidin.moneyverse.domain.model.transaction

import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity

data class Transaction(
    var id: Int = 0,
    var nominal: Long,
    var createdAt: String,
    var notes: String? = null,
    var transactionType: TransactionType = TransactionType.OUTCOME,
    var categoryId: Int,
    var fromAccountId: Int,
    var toAccountId: Int? = null,
) {
    fun toEntity() = TransactionEntity(
        id = id,
        nominal = nominal,
        createdAt = createdAt,
        notes = notes,
        transactionType = transactionType,
        categoryId = categoryId,
        fromAccountId = fromAccountId,
        toAccountId = toAccountId
    )
}
