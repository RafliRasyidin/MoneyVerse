package com.rasyidin.moneyverse.data.local.entities.anggaran

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidin.moneyverse.domain.model.anggaran.Anggaran
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType

@Entity(tableName = "anggaran")
data class AnggaranEntity(
    @PrimaryKey
    var id: String,

    @ColumnInfo("transactionId")
    var transactionId: String,

    @ColumnInfo("categoryId")
    var categoryId: Int,

    @ColumnInfo("nominalAnggaran")
    var nominalAnggaran: Long,

    @ColumnInfo("createdAt")
    var createdAt: String,

    @ColumnInfo("anggaranBerulang")
    var anggaranBerulang: Int,

    @ColumnInfo("anggaranType")
    var anggaranType: AnggaranType = AnggaranType.MONTHLY,

    @ColumnInfo("jadwalAnggaran")
    var anggaranSchedule: String
) {
    fun toDomain() = Anggaran(
        id = id,
        transactionId = transactionId,
        categoryId = categoryId,
        nominalAnggaran = nominalAnggaran,
        createdAt = createdAt,
        anggaranBerulang = anggaranBerulang,
        anggaranType = anggaranType,
        anggaranSchedule = anggaranSchedule
    )
}

