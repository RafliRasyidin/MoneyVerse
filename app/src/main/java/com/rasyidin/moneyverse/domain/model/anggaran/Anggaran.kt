package com.rasyidin.moneyverse.domain.model.anggaran

import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranEntity

data class Anggaran(
    var id: String,
    var transactionId: String,
    var categoryId: Int,
    var nominalAnggaran: Long,
    var createdAt: String,
    var anggaranBerulang: Int,
    var anggaranType: AnggaranType = AnggaranType.MONTHLY,
    var anggaranSchedule: String
) {
    fun toEntity() = AnggaranEntity(
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
