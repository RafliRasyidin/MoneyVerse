package com.rasyidin.moneyverse.utils

object AnggaranUtils {

    fun getExpenditureRange(totalAnggaran: Long, totalPengeluaran: Long): ExpenditureCategory {
        val expenditurePercentage = (totalPengeluaran.toDouble() / totalAnggaran.toDouble()) * 100
        return when {
            expenditurePercentage >= 100.0 -> ExpenditureCategory.DANGER
            expenditurePercentage >= 75.0 -> ExpenditureCategory.WARNING
            else -> ExpenditureCategory.SAFE
        }
    }
}

enum class ExpenditureCategory {
    DANGER,
    WARNING,
    SAFE
}