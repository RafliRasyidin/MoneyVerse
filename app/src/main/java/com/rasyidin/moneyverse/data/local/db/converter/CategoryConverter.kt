package com.rasyidin.moneyverse.data.local.db.converter

import androidx.room.TypeConverter
import com.rasyidin.moneyverse.domain.model.category.CategoryType

class CategoryConverter {

    @TypeConverter
    fun toCategoryType(value: String): CategoryType {
        return when (value) {
            CategoryType.TransactionOutcome.name -> CategoryType.TransactionOutcome
            CategoryType.TransactionIncome.name -> CategoryType.TransactionIncome
            CategoryType.AddIncome.name -> CategoryType.AddIncome
            else -> throw IllegalStateException("Illegal category type, expected CategoryType founded $value")
        }
    }

    @TypeConverter
    fun fromCategoryType(categoryType: CategoryType): String {
        return when (categoryType) {
            CategoryType.TransactionOutcome -> CategoryType.TransactionOutcome.name
            CategoryType.TransactionIncome -> CategoryType.TransactionIncome.name
            CategoryType.AddIncome -> CategoryType.AddIncome.name
        }
    }
}