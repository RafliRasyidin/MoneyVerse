package com.rasyidin.moneyverse.data.repository.anggaran

import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranDao
import com.rasyidin.moneyverse.data.local.entities.anggaran.AnggaranEntity
import javax.inject.Inject

class AnggaranRepositoryImpl @Inject constructor(
    private val anggaranDao: AnggaranDao,
) : AnggaranRepository {

    override suspend fun getTotalSaldo(): Long {
        return anggaranDao.getTotalAnggaran()
    }

    override suspend fun upsertAnggaran(anggaranEntity: AnggaranEntity) {
        anggaranDao.upsertAnggaran(anggaranEntity)
    }
}