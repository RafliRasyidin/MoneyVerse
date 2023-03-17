package com.rasyidin.moneyverse.data.local.entities.category

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.moneyverse.domain.model.category.CategoryType
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType

@Entity("category")
data class CategoryEntity(
    @PrimaryKey
    var id: Int,
    @DrawableRes var bgColor: Int,
    @DrawableRes var iconPath: Int,
    var name: String,
    var categoryType: CategoryType = CategoryType.TransactionOutcome
)
