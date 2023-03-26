package com.rasyidin.moneyverse.domain.model.transaction

data class ItemTransaction(
    val id: Int,
    val nominal: Long,
    val createdAt: String,
    val notes: String? = null,
    val transactionType: TransactionType,
    val categoryId: Int,
    val fromAccountId: Int,
    val toAccountId: Int? = null,
    val iconPath: Int? = null,
    val bgColor: Int? = null,
    val categoryName: String? = null,
    val accountName: String
)
