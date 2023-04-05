package com.rasyidin.moneyverse.data.repository.anggaran

import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranEntity

interface AnggaranRepository {

    suspend fun getTotalSaldo(): Long

    suspend fun upsertAnggaran(anggaranEntity: AnggaranEntity)
}