package com.rasyidin.moneyverse.data.local.db.converter

import androidx.room.TypeConverter
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.*

class AnggaranConverter {

    @TypeConverter
    fun toAnggaranType(value: String): AnggaranType {
        return when (value) {
            DAILY.name -> DAILY
            WEEKLY.name -> WEEKLY
            MONTHLY.name -> MONTHLY
            ANNUAL.name -> ANNUAL
            else -> throw IllegalStateException("Illegal anggaran type, expected AnggaranType name founded $value")
        }
    }

    @TypeConverter
    fun fromAnggaranType(anggaranType: AnggaranType): String {
        return when (anggaranType) {
            DAILY -> DAILY.name
            WEEKLY -> WEEKLY.name
            MONTHLY -> MONTHLY.name
            ANNUAL -> ANNUAL.name
        }
    }
}