package com.rasyidin.moneyverse.domain.usecase.anggaran.add

import com.rasyidin.moneyverse.domain.usecase.anggaran.UpsertAnggaran

data class AddAnggaranUseCase(
    val upsertAnggaran: UpsertAnggaran,
    val getCategories: GetCategories
)
