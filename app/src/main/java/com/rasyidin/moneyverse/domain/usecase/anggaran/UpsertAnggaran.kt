package com.rasyidin.moneyverse.domain.usecase.anggaran

import com.rasyidin.moneyverse.data.repository.anggaran.AnggaranRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.model.anggaran.Anggaran
import com.rasyidin.moneyverse.domain.performAction
import kotlinx.coroutines.flow.Flow

class UpsertAnggaran(private val anggaranRepo: AnggaranRepository) {

    suspend operator fun invoke(anggaran: Anggaran): Flow<ResultState<Nothing>> {
        return performAction {
            anggaranRepo.upsertAnggaran(anggaran.toEntity())
        }
    }
}