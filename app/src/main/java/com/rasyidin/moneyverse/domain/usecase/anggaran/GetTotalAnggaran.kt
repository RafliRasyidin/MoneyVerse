package com.rasyidin.moneyverse.domain.usecase.anggaran

import com.rasyidin.moneyverse.data.repository.anggaran.AnggaranRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetch
import kotlinx.coroutines.flow.Flow

class GetTotalAnggaran(private val anggaranRepo: AnggaranRepository) {

    suspend operator fun invoke(): Flow<ResultState<Long>> {
        return fetch {
            anggaranRepo.getTotalSaldo()
        }
    }
}