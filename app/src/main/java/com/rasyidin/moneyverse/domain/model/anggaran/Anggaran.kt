package com.rasyidin.moneyverse.domain.model.anggaran

import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranEntity

data class Anggaran(
    var id: String,
    var categoryId: Int,
    var nominalAnggaran: Long,
    var createdAt: String,
    var anggaranBerulang: Int,
    var anggaranType: AnggaranType = AnggaranType.MONTHLY,
    var startDate: String,
    var endDate: String
) {
    fun toEntity() = AnggaranEntity(
        id = id,
         categoryId = categoryId,
        nominalAnggaran = nominalAnggaran,
        createdAt = createdAt,
        anggaranBerulang = anggaranBerulang,
        anggaranType = anggaranType,
        startDate = startDate,
        endDate = endDate
    )
}
